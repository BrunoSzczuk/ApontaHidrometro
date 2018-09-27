/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Estado;
import br.com.brunoszczuk.apontahidrometro.entities.Pais;
import br.com.brunoszczuk.apontahidrometro.repository.EstadoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.PaisRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
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
 * @author bruno
 */
@Controller
@RequestMapping("estado")
public class EstadoController {

    @Autowired
    EstadoRepository repo;

    @Autowired
    PaisRepository paises;

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("estados", repo.findAll(new Sort(Sort.Direction.ASC, "cdEstado")));
        model.addAttribute("conteudo", "/estado/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Estado p,  ModelMap model) {
        model.addAttribute("conteudo", "/estado/add");
        model.addAttribute("paises", getPaises());
        return new ModelAndView("layout", "conteudo", "/estado/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Estado estado, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/estado/add");
        model.addAttribute("paises", getPaises());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(estado);

        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/estado/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Estado estado, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/estado/add");
        model.addAttribute("paises", getPaises());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(estado);
        attrib.addFlashAttribute("message", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/estado/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") String id, ModelMap model) {
        Estado e = repo.findById(id).get();
        model.addAttribute("estado", e);
        model.addAttribute("paises", getPaises());
        model.addAttribute("conteudo", "/estado/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attrib) {
        repo.deleteById(id);
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/estado/";
    }

    @ModelAttribute
    public List<Pais> getPaises() {
        return paises.findAll();
    }
}
