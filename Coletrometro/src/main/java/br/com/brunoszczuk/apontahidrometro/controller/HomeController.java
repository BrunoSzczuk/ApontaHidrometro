package br.com.brunoszczuk.apontahidrometro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }
    
    @GetMapping("/")
    public ModelAndView home(){
        return new ModelAndView("layout", "conteudo", "/index");
    }
    
    @GetMapping("/index2")
    public ModelAndView home2(){
        return new ModelAndView("index2", "conteudo", "/index");
    }
}
