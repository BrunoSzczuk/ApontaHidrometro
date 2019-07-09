/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Permissao;
import br.com.brunoszczuk.apontahidrometro.repository.PermissaoRepository;
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
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("permissao")
public class PermissaoController {

    @Autowired
    PermissaoRepository repo;

    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());
    
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("permissoes", repo.findAll(new Sort(Sort.Direction.ASC, "cdPermissao")));
        model.addAttribute("conteudo", "/permissao/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Permissao p) {
        return new ModelAndView("layout", "conteudo", "/permissao/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Permissao permissao, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/permissao/add");
        }

        repo.save(permissao);

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/permissao/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Permissao permissao, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/permissao/add");
        }

        repo.save(permissao);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/permissao/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Permissao e = repo.findById(id).get();
        model.addAttribute("permissao", e);
        model.addAttribute("conteudo", "/permissao/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));
            
        }catch(DataIntegrityViolationException ex){
            attrib.addFlashAttribute("errorMessage",bundle.getString("lbexistedependencia"));
        }
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/permissao/";
    }
}
