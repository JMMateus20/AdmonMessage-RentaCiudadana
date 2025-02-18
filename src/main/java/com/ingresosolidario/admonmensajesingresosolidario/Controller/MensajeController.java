package com.ingresosolidario.admonmensajesingresosolidario.Controller;

import com.ingresosolidario.admonmensajesingresosolidario.models.Mensaje;
import com.ingresosolidario.admonmensajesingresosolidario.repository.MensajeRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MensajeController {
    @Autowired
    private MensajeRepository mensajeRep;
    @Autowired
    private LoginController loginController;

    @GetMapping("/canales")
    String listarCanales(Model model, HttpServletRequest request){
        boolean sesionExpirada=this.sesionExpirada(request);
        if (sesionExpirada){
            return "login";
        }
        HttpSession session=request.getSession(false);
        List<Mensaje> mensajes = this.mensajeRep.findAll();
        model.addAttribute("canales", mensajes);
        model.addAttribute("nombreUsuario", (String) session.getAttribute("nombre") + " " + (String) session.getAttribute("apellido"));
        model.addAttribute("vicep", session.getAttribute("vicepresidencia"));
        return "canales";
    }

    @PostMapping("/actualizar")
    ResponseEntity<Map<String, Object>> actualizarMensaje(@RequestParam("canal") Long id, @RequestParam("mensaje") String mensaje, HttpServletRequest request){
        Map<String, Object> respuesta=new HashMap<>();
        if (this.sesionExpirada(request)){
            respuesta.put("Mensaje", "expirado");
            return ResponseEntity.ok(respuesta);
        }
        if (!mensaje.isEmpty()){
            Mensaje mensajeBD=this.mensajeRep.findById(id).orElseThrow(()->new RuntimeException());
            mensajeBD.setMensaje(mensaje);
            this.mensajeRep.save(mensajeBD);

            respuesta.put("Titulo", "Exito");
            respuesta.put("Mensaje", "Mensaje actualizado");
            respuesta.put("canales", this.mensajeRep.findAll());
            return ResponseEntity.ok(respuesta);
        }else{
            respuesta.put("Titulo", "Error");
            respuesta.put("Mensaje", "Debe escribir el mensaje");
            return ResponseEntity.ok(respuesta);
        }


    }


    @PostMapping("/registrar")
    ResponseEntity<Map<String, Object>> registrarNuevoCanal(@RequestBody Mensaje mensaje, HttpServletRequest request){
        Map<String, Object> respuesta=new HashMap<>();
        if (this.sesionExpirada(request)){
            respuesta.put("Mensaje", "expirado");
            return ResponseEntity.ok(respuesta);
        }
        if ((!mensaje.getCanal().isEmpty() && mensaje.getCanal()!=null) && (!mensaje.getMensaje().isEmpty() && mensaje.getMensaje()!=null)){
            Mensaje mensajeNew=this.mensajeRep.save(mensaje);
            respuesta.put("mensajeObject", mensajeNew);
            respuesta.put("Titulo", "Canal guardado");
            respuesta.put("Mensaje", "Canal registrado con éxito");
            return ResponseEntity.ok(respuesta);
        }else{
            respuesta.put("Titulo", "Error");
            respuesta.put("Mensaje", "Debe ingresar todos los campos");
            return ResponseEntity.badRequest().body(respuesta);
        }
    }


    private boolean sesionExpirada(HttpServletRequest request){
        HttpSession sesion=request.getSession(false);
        if (sesion==null){
            return true;
        }
        String ldapAsesor=(String) sesion.getAttribute("ldapAsesor");
        Long expiracion=(Long) sesion.getAttribute("expiration");


        if (ldapAsesor == null || ldapAsesor.isEmpty() || (expiracion != null && System.currentTimeMillis() > expiracion)) {
            sesion.invalidate();
            return true;
        }
        return false;
    }


}
