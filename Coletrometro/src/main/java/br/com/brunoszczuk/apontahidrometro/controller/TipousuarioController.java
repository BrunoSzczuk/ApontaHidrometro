/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Permissaotipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
import br.com.brunoszczuk.apontahidrometro.repository.PermissaoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.PermissaotipousuarioRepository;
import br.com.brunoszczuk.apontahidrometro.repository.TipousuarioRepository;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bruno
 */
@Controller
@RequestMapping("tipousuario")
public class TipousuarioController {

    @Autowired
    TipousuarioRepository repo;
    @Autowired
    PermissaotipousuarioRepository permissaotipousuarios;

    @Autowired
    PermissaoRepository permissoes;

    List<Permissaotipousuario> lista = new ArrayList<>();
    
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("tipousuarios", repo.findAll(new Sort(Sort.Direction.ASC, "cdTipousuario")));
        model.addAttribute("conteudo", "/tipousuario/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Tipousuario p, ModelMap model) {
        return new ModelAndView("layout", getModel(model));
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Tipousuario tipousuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", getModel(model));
        }

        repo.save(tipousuario);

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/tipousuario/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Tipousuario tipousuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", getModel(model));
        }

        repo.save(tipousuario);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/tipousuario/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") String id, ModelMap model) {
        Tipousuario e = repo.findById(id).get();
        model = getModel(model);
        model.addAttribute("tipousuario", e);
        return new ModelAndView("layout", model);
    }

    
    @GetMapping("/permissoes/{id}")
    public ModelAndView updatePermissoes(@PathVariable("id") String id, ModelMap model) {
        Tipousuario e = repo.findById(id).get();
        model.addAttribute("conteudo", "/tipousuario/permissao");
        Tipousuario tipo = repo.findById(id).get();

        lista.clear();
        permissoes.findAll().forEach(obj -> lista.add(new Permissaotipousuario(obj.getCdPermissao(),obj, tipo, null, null)));
        model.addAttribute("permissoes", lista);
        model.addAttribute("tipousuario", e);
        return new ModelAndView("layout", model);
    }
    
    @PostMapping("/permissoes/save")
    public ModelAndView savePermissoes(Tipousuario tipousuario, @RequestParam("permissaotipousuarios") List<Permissaotipousuario> ptu,BindingResult result, RedirectAttributes attrib, ModelMap model) {
              
        tipousuario.getPermissaotipousuarios().forEach(p -> p.getTipousuario().setCdTipousuario(tipousuario.getCdTipousuario()));
        permissaotipousuarios.saveAll(tipousuario.getPermissaotipousuarios());

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/tipousuario/");
    }
    
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbregistroexistente"));
        };
        return "redirect:/tipousuario/";
    }

    @ModelAttribute
    private List<Tipousuario> getTipousuario() {
        return repo.findAll();
    }
    private ModelMap getModel(ModelMap model) {
        model.addAttribute("tipousuarios", repo.findAll(new Sort(Sort.Direction.ASC, "cdTipousuario")));
        model.addAttribute("conteudo", "/tipousuario/add");
        return model;
    }
}
