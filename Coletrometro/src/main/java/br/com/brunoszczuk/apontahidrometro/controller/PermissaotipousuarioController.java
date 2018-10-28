/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Permissao;
import br.com.brunoszczuk.apontahidrometro.entities.Permissaotipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
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
import br.com.brunoszczuk.apontahidrometro.repository.PermissaotipousuarioRepository;
import br.com.brunoszczuk.apontahidrometro.repository.TipousuarioRepository;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("permissaotipousuario")
public class PermissaotipousuarioController {

    @Autowired
    PermissaotipousuarioRepository repo;

    @Autowired
    TipousuarioRepository tipousuarios;

    @Autowired
    PermissaoRepository permissoes;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("permissoes", repo.findAll(new Sort(Sort.Direction.ASC, "cdPermissaotipousuario")));
        model.addAttribute("conteudo", "/permissaotipousuario/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Permissaotipousuario p, ModelMap model) {
        return new ModelAndView("layout", getModel(model));
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Permissaotipousuario permissaotipousuario, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/permissaotipousuario/add");
        }

        repo.save(permissaotipousuario);

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/permissaotipousuario/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Permissaotipousuario permissaotipousuario, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/permissaotipousuario/add");
        }

        repo.save(permissaotipousuario);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/permissaotipousuario/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Permissaotipousuario e = repo.findById(id).get();
        model = getModel(model);
        model.addAttribute("permissaotipousuario", e);
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
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/permissaotipousuario/";
    }

    @ModelAttribute
    private List<Tipousuario> getTipousuario() {
        return tipousuarios.findAll();
    }

    @ModelAttribute
    private List<Permissao> getPermissao() {
        return permissoes.findAll();
    }

    private ModelMap getModel(ModelMap model) {
        model.addAttribute("permissoes", getPermissao());
        model.addAttribute("tipousuario", getTipousuario());
        model.addAttribute("conteudo", "/permissaotipousuario/add");
        return model;
    }
}
