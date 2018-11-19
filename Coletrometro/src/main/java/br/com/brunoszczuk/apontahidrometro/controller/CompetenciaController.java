/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Competencia;
import br.com.brunoszczuk.apontahidrometro.repository.CompetenciaRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("competencia")
public class CompetenciaController {
    
    @Autowired
    CompetenciaRepository repo;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("competencias", repo.findAll(new Sort(Sort.Direction.ASC, "cdCompetencia")));
        model.addAttribute("conteudo", "/competencia/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Competencia p, ModelMap model) {
        model = getModel(model);
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Competencia competencia, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (competencia.getDtInclusao() == null){
            competencia.setDtInclusao( LocalDate.now());
        }
        repo.save(competencia);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/competencia/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Competencia competencia, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(competencia);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/competencia/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        if (repo.existsById(id)) {
            Competencia e = repo.findById(id).get();
            model = getModel(model);
            model.addAttribute("competencia", e);
            
        }
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbregistroexistente"));
        }
        return "redirect:/competencia/";
    }

    private ModelMap getModel(ModelMap model) {
        model.addAttribute("conteudo", "/competencia/add");
        return model;
    }
}
