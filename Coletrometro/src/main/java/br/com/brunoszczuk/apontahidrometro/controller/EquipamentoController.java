/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository eq;
    
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("equipamentos", eq.findAll());
        model.addAttribute("conteudo", "/equipamento/list");
        return new ModelAndView("layout", model);
    }
}
