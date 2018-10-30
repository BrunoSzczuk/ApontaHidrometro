/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Tabpreco;
import br.com.brunoszczuk.apontahidrometro.entities.Endereco;
import br.com.brunoszczuk.apontahidrometro.entities.Formapagto;
import br.com.brunoszczuk.apontahidrometro.repository.TabprecoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EnderecoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FormaPagtoRepository;
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
@RequestMapping("tabpreco")
public class TabprecoController {

    @Autowired
    TabprecoRepository repo;


    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("tabprecos", repo.findAll(new Sort(Sort.Direction.ASC, "cdTabpreco")));
        model.addAttribute("conteudo", "/tabpreco/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Tabpreco p, ModelMap model) {
        model.addAttribute("conteudo", "/tabpreco/add");
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Tabpreco tabpreco, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/tabpreco/add");
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(tabpreco);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/tabpreco/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Tabpreco tabpreco, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/tabpreco/add");
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(tabpreco);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/tabpreco/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Tabpreco e = repo.findById(id).get();
        model.addAttribute("tabpreco", e);
        model.addAttribute("conteudo", "/tabpreco/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));
            
        }catch(DataIntegrityViolationException ex){
            attrib.addFlashAttribute("errorMessage",bundle.getString("lbregistroexistente"));
        }
        return "redirect:/tabpreco/";
    }

}
