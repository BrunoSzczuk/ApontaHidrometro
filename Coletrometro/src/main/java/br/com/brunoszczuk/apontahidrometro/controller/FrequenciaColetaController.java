/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Frequenciacoleta;
import br.com.brunoszczuk.apontahidrometro.repository.FrequenciaColetaRepository;
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
 * @author bruno
 */
@Controller
@RequestMapping("frequenciacoleta")
public class FrequenciaColetaController {

    @Autowired
    FrequenciaColetaRepository repo;


    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("frequenciacoletas", repo.findAll(new Sort(Sort.Direction.ASC, "cdFrequenciacoleta")));
        model.addAttribute("conteudo", "/frequenciacoleta/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Frequenciacoleta p, ModelMap model) {
        p.setCdFrequenciacoleta((int)repo.count() +1 );
        model.addAttribute("conteudo", "/frequenciacoleta/add");
        return new ModelAndView("layout", "conteudo", "/frequenciacoleta/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Frequenciacoleta frequenciacoleta, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/frequenciacoleta/add");
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (repo.findById(frequenciacoleta.getCdFrequenciacoleta()) != null) {
            frequenciacoleta.setCdFrequenciacoleta((int) (repo.count() + 1));
        }
        repo.save(frequenciacoleta);
        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/frequenciacoleta/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Frequenciacoleta frequenciacoleta, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/frequenciacoleta/add");
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(frequenciacoleta);
        attrib.addFlashAttribute("message", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/frequenciacoleta/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Frequenciacoleta e = repo.findById(id).get();
        model.addAttribute("frequenciacoleta", e);
        model.addAttribute("conteudo", "/frequenciacoleta/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        repo.deleteById(id);
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/frequenciacoleta/";
    }

}
