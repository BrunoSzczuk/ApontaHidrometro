/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import br.com.brunoszczuk.apontahidrometro.util.ValidatorUtil;
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
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository repo;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("equipamentos", repo.findAll(new Sort(Sort.Direction.ASC, "cdEquipamento")));
        model.addAttribute("conteudo", "/equipamento/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Equipamento e) {
        return new ModelAndView("layout", "conteudo", "/equipamento/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/equipamento/add");
        model.addAttribute("equipamento", equipamento);
        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/equipamento/add");
        }
        if (repo.existsByNrSerieIgnoreCase(equipamento.getNrSerie().trim())){
            result.addError(ValidatorUtil.addErrorField(equipamento, "nrSerie",bundle.getString("lbregistroexistente")));    
            return new ModelAndView("layout", model);
        }
        if (equipamento.getContAtual() < equipamento.getContInicial()){
            attrib.addFlashAttribute("message", bundle.getString("message.equipamento.contadorinvalido"));
            attrib.addFlashAttribute("equipamento", equipamento);
            return new ModelAndView("redirect:/equipamento/add");
        }
        repo.save(equipamento);

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/equipamento/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attrib) {

        if (result.hasErrors()) {
            return new ModelAndView("layout", "conteudo", "/equipamento/add");
        }

        repo.save(equipamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/equipamento/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") Integer id, ModelMap model) {
        Equipamento e = repo.findById(id).get();
        model.addAttribute("equipamento", e);
        model.addAttribute("conteudo", "/equipamento/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbexistedependencia"));
        }
        return "redirect:/equipamento/";
    }

}
