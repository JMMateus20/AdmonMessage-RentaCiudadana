package com.ingresosolidario.admonmensajesingresosolidario.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingresosolidario.admonmensajesingresosolidario.DTO.UserDTO;
import com.ingresosolidario.admonmensajesingresosolidario.Service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ProxyController {


    @Autowired
    private LoginService loginService;

    private final long expirationDuration = 2 * 60 * 1000;



    @PostMapping("/proxy/token")
    public ResponseEntity<?> proxyToken(@RequestParam MultiValueMap<String, String> params, HttpServletRequest request) throws JsonProcessingException {

        ResponseEntity<String> response=this.loginService.login(params);


        request.getSession().setAttribute("expiration", System.currentTimeMillis() + expirationDuration);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);


        return ResponseEntity.status(response.getStatusCode())
                .headers(responseHeaders)
                .body(response.getBody());


    }


    @PostMapping("/proxy/directorioActivoApi")
    public ResponseEntity<String> sendJsonToService(@RequestBody Map<String, Object> requestBody, @RequestHeader("Authorization") String token, HttpServletRequest request) throws JsonProcessingException {
        ResponseEntity<String> response=this.loginService.requestApiDirectorioActivo(requestBody, token);

        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, Object> jsonBodyResponse = objectMapper.readValue(response.getBody(), Map.class);
        String infoRespuesta=(String) jsonBodyResponse.get("Response");
        HttpSession session=request.getSession(false);
        if (infoRespuesta.equals("Success")){
            UserDTO userDTO=objectMapper.readValue(response.getBody(), UserDTO.class);


            session.setAttribute("ldapAsesor", userDTO.getLdapAsesor());
            session.setAttribute("nombre", userDTO.getNombre());
            session.setAttribute("apellido", userDTO.getApellido());
            session.setAttribute("vicepresidencia", userDTO.getVicepresidencia());
        }else{
            session.invalidate();
        }


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);


        return ResponseEntity.status(response.getStatusCode())
                .headers(responseHeaders)
                .body(response.getBody());

    }



}
