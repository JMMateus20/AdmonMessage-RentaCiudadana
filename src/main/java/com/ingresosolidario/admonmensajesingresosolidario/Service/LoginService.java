package com.ingresosolidario.admonmensajesingresosolidario.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LoginService {

    private final String API_MANAGER_LOGIN_URL = "https://apimanager.bancoagrario.gov.co:9443/oauth2/token";
    private final String AUTHORIZATION_HEADER = "Aca el header necesario para la autenticacion (Bearer o Basic)";
    private final String API_MANAGER_DIRECTORIO_ACTIVO_URL= "https://apimanager.bancoagrario.gov.co:8243/gestionarusuariosldap/1.0.0/buscarusuario";

    private final RestTemplate restTemplate;

    @Autowired
    LoginService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> login(@RequestParam MultiValueMap<String, String> params){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTHORIZATION_HEADER);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        return this.restTemplate.exchange(
                API_MANAGER_LOGIN_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
    }

    public ResponseEntity<String> requestApiDirectorioActivo(Map<String, Object> body, String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        return this.restTemplate.exchange(
                API_MANAGER_DIRECTORIO_ACTIVO_URL, HttpMethod.POST, requestEntity, String.class);



    }
}
