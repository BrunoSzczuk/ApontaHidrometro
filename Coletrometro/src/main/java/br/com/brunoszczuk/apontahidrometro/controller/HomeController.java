package br.com.brunoszczuk.apontahidrometro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Ballem on 31/07/2017.
 */
@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }
    
    @GetMapping("/")
    public String home(){
        return "redirect:/equipamento/";
    }
}
