package com.ingresosolidario.admonmensajesingresosolidario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {



    @GetMapping("/login")
    String pagLogin(){
        return "login";
    }



}
