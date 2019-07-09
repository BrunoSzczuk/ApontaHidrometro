/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Endereco;
import br.com.brunoszczuk.apontahidrometro.entities.Municipio;
import br.com.brunoszczuk.apontahidrometro.repository.EnderecoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.MunicipioRepository;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("endereco")
public class EnderecoController {

    @Autowired
    EnderecoRepository repo;

    @Autowired
    MunicipioRepository municipios;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("enderecos", repo.findAll(new Sort(Sort.Direction.ASC, "cdEndereco")));
        model.addAttribute("conteudo", "/endereco/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Endereco p, ModelMap model) {
        p.setCdEndereco((int) repo.count() + 1);
        model.addAttribute("conteudo", "/endereco/add");
        model.addAttribute("municipios", getMunicipios());
        return new ModelAndView("layout", "conteudo", "/endereco/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Endereco endereco, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/endereco/add");
        model.addAttribute("municipios", getMunicipios());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (repo.findById(endereco.getCdEndereco()) != null) {
            endereco.setCdEndereco((int) (repo.count() + 1));
        }
        repo.save(endereco);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/endereco/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Endereco endereco, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/endereco/add");
        model.addAttribute("municipios", getMunicipios());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(endereco);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/endereco/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Endereco e = repo.findById(id).get();
        model.addAttribute("endereco", e);
        model.addAttribute("municipios", getMunicipios());
        model.addAttribute("conteudo", "/endereco/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbexistedependencia"));
        }
        return "redirect:/endereco/";
    }

    @ModelAttribute
    public List<Municipio> getMunicipios() {
        return municipios.findByStAtivoTrue();
    }
}
