/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Condicaopagto;
import br.com.brunoszczuk.apontahidrometro.repository.CondicaopagtoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("condicaopagto")
public class CondicaopagtoController {

    @Autowired
    CondicaopagtoRepository repo;

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("condicaopagtos", repo.findAll(new Sort(Sort.Direction.ASC, "cdCondicaopagto")));
        model.addAttribute("conteudo", "/condicaopagto/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Condicaopagto p) {
        return new ModelAndView("layout", "conteudo", "/condicaopagto/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Condicaopagto condicaopagto, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/condicaopagto/add");
        }

        repo.save(condicaopagto);

        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/condicaopagto/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Condicaopagto condicaopagto, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/condicaopagto/add");
        }

        repo.save(condicaopagto);
        attrib.addFlashAttribute("message", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/condicaopagto/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") String id, ModelMap model) {
        Condicaopagto e = repo.findById(id).get();
        model.addAttribute("condicaopagto", e);
        model.addAttribute("conteudo", "/condicaopagto/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attrib) {
        repo.deleteById(id);
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/condicaopagto/";
    }
}
