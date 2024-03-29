/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Estado;
import br.com.brunoszczuk.apontahidrometro.entities.Municipio;
import br.com.brunoszczuk.apontahidrometro.repository.EstadoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.MunicipioRepository;
import java.util.List;
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
@RequestMapping("municipio")
public class MunicipioController {

    @Autowired
    MunicipioRepository repo;

    @Autowired
    EstadoRepository estados;
    
    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());
    
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("municipios", repo.findAll(new Sort(Sort.Direction.ASC, "cdMunicipio")));
        model.addAttribute("conteudo", "/municipio/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Municipio p, ModelMap model) {
        model.addAttribute("conteudo", "/municipio/add");
        model.addAttribute("estados", getEstados());
        return new ModelAndView("layout", "conteudo", "/municipio/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Municipio municipio, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/municipio/add");
        model.addAttribute("estados", getEstados());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(municipio);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/municipio/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Municipio municipio, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/municipio/add");
        model.addAttribute("estados", getEstados());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(municipio);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/municipio/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") String id, ModelMap model) {
        Municipio e = repo.findById(id).get();
        model.addAttribute("municipio", e);
        model.addAttribute("estados", getEstados());
        model.addAttribute("conteudo", "/municipio/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));
            
        }catch(DataIntegrityViolationException ex){
            attrib.addFlashAttribute("errorMessage",bundle.getString("lbexistedependencia"));
        }
        return "redirect:/municipio/";
    }

    @ModelAttribute
    public List<Estado> getEstados() {
        return estados.findByStAtivoTrue();
    }
}
