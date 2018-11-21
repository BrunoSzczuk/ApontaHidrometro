/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Contareceber;
import br.com.brunoszczuk.apontahidrometro.entities.Cliente;
import br.com.brunoszczuk.apontahidrometro.entities.Formapagto;
import br.com.brunoszczuk.apontahidrometro.repository.ContareceberRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ClienteRepository;
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
@RequestMapping("contareceber")
public class ContareceberController {

    @Autowired
    ContareceberRepository repo;

    @Autowired
    ClienteRepository clientes;

    @Autowired
    FormaPagtoRepository formapagtos;

    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("contarecebers", repo.findAll(new Sort(Sort.Direction.ASC, "cdContareceber")));
        model.addAttribute("conteudo", "/contareceber/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Contareceber p, ModelMap model) {
        return new ModelAndView("layout", getModel(model));
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Contareceber contareceber, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(contareceber);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/contareceber/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Contareceber contareceber, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(contareceber);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/contareceber/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Contareceber e = repo.findById(id).get();
        model.addAttribute("contareceber", e);
        
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
        return "redirect:/contareceber/";
    }

    @ModelAttribute
    public List<Cliente> getClientes() {
        return clientes.findAll();
    }

    @ModelAttribute
    public List<Formapagto> getFormapagtos() {
        return formapagtos.findAll();
    }
    
    private ModelMap getModel(ModelMap model){
        model.addAttribute("clientes", getClientes());
        model.addAttribute("formapagtos", getFormapagtos());
        model.addAttribute("conteudo", "/contareceber/add");
        return model;
    }
    
}
