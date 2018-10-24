/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Cliente;
import br.com.brunoszczuk.apontahidrometro.entities.Condicaopagto;
import br.com.brunoszczuk.apontahidrometro.entities.Frequenciacoleta;
import br.com.brunoszczuk.apontahidrometro.entities.Unidadeconsumidora;
import br.com.brunoszczuk.apontahidrometro.repository.ClienteRepository;
import br.com.brunoszczuk.apontahidrometro.repository.CondicaopagtoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FrequenciaColetaRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UnidadeconsumidoraRepository;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
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
@RequestMapping("contrato")
public class ContratoController {

    @Autowired
    ContratoRepository repo;

    @Autowired
    ClienteRepository clientes;

    @Autowired
    FrequenciaColetaRepository frequenciacoletas;

    @Autowired
    CondicaopagtoRepository condicaopagtos;

    @Autowired
    UnidadeconsumidoraRepository unidadeConsumidoras;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("contratos", repo.findAll(new Sort(Sort.Direction.ASC, "nrContrato")));
        model.addAttribute("conteudo", "/contrato/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Contrato p, ModelMap model) {
        model = getModel(model);
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Contrato contrato, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(contrato);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/contrato/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Contrato contrato, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model = getModel(model);
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(contrato);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/contrato/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") String id, ModelMap model) {
        if (repo.existsById(id)) {
            Contrato e = repo.findById(id).get();
            model = getModel(model);
            model.addAttribute("contrato", e);
            return new ModelAndView("layout", model);
        }else{
            throw new NotFoundException();
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbregistroexistente"));
        }
        return "redirect:/contrato/";
    }

    @ModelAttribute
    public List<Cliente> getClientes() {
        return clientes.findAll();
    }

    @ModelAttribute
    public List<Frequenciacoleta> getFrequenciacoletas() {
        return frequenciacoletas.findAll();
    }

    @ModelAttribute
    public List<Condicaopagto> getCondicaopagtos() {
        return condicaopagtos.findAll();
    }

    @ModelAttribute
    public List<Unidadeconsumidora> getUnidadeconsumidoras() {
        return unidadeConsumidoras.findAll();
    }

    private ModelMap getModel(ModelMap model) {
        model.addAttribute("unidadeconsumidoras", getUnidadeconsumidoras());
        model.addAttribute("clientes", getClientes());
        model.addAttribute("condicaopagtos", getCondicaopagtos());
        model.addAttribute("frequenciacoletas", getFrequenciacoletas());
        model.addAttribute("conteudo", "/contrato/add");
        return model;
    }
}
