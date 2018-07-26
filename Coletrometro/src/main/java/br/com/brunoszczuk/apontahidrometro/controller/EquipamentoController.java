/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.domain.Equipamento;
import br.com.brunoszczuk.apontahidrometro.domain.Status;
import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("equipamentos", eq.findAll(new Sort(Sort.Direction.ASC, "cdEquipamento")));
        model.addAttribute("conteudo", "/equipamento/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Equipamento eq) {
        return new ModelAndView("layout", "conteudo", "/equipamento/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/equipamento/add");
        }

        eq.save(equipamento);
        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/equipamento/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/equipamento/add");
        }
        eq.save(equipamento);
        attrib.addFlashAttribute("message", "Registro editado com sucesso.");
        return new ModelAndView("redirect:/equipamento/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") Long id, ModelMap model) {
        Equipamento e = eq.findById(id).get();
        model.addAttribute("equipamento", e);
        model.addAttribute("conteudo", "/equipamento/add");
        return new ModelAndView("layout", model);
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attrib) {
        eq.deleteById(id);
        attrib.addFlashAttribute("message", "Equipamento removido com sucesso.");
        return "redirect:/equipamento/";
    }

    @ModelAttribute("status")
    public Status[] tipoStatus() {
        return Status.values();
    }
}
