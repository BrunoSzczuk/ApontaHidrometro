/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Contratousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Usuario;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratousuarioRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import java.util.Date;
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
@RequestMapping("contratousuario")
public class ContratousuarioController {

    @Autowired
    ContratoRepository contratos;

    @Autowired
    UsuarioRepository usuarios;

    @Autowired
    ContratousuarioRepository repo;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("contratousuarios", repo.findAll(new Sort(Sort.Direction.ASC, "cdContratousuario")));
        model.addAttribute("conteudo", "/contratousuario/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Contratousuario p, ModelMap model) {
        model = getModel(model);
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Contratousuario contratousuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (contratousuario.getDtInclusao() == null){
            contratousuario.setDtInclusao(new Date());
        }
        repo.save(contratousuario);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/contratousuario/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Contratousuario contratousuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(contratousuario);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/contratousuario/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        if (repo.existsById(id)) {
            Contratousuario e = repo.findById(id).get();
            model = getModel(model);
            model.addAttribute("contratousuario", e);
            
        }
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
        return "redirect:/contratousuario/";
    }

    @ModelAttribute
    public List<Contrato> getContratos() {
        return contratos.findAll();
    }

    @ModelAttribute
    public List<Usuario> getUsuarios() {
        return usuarios.findAll();
    }
    private ModelMap getModel(ModelMap model) {
        model.addAttribute("contratos", getContratos());
        model.addAttribute("usuarios", getUsuarios());
        model.addAttribute("conteudo", "/contratousuario/add");
        return model;
    }
}
