/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Cliente;
import br.com.brunoszczuk.apontahidrometro.entities.Endereco;
import br.com.brunoszczuk.apontahidrometro.entities.Formapagto;
import br.com.brunoszczuk.apontahidrometro.repository.ClienteRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EnderecoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FormaPagtoRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    ClienteRepository repo;

    @Autowired
    EnderecoRepository enderecos;

    @Autowired
    FormaPagtoRepository formapagtos;

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("clientes", repo.findAll(new Sort(Sort.Direction.ASC, "cdCliente")));
        model.addAttribute("conteudo", "/cliente/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Cliente p, ModelMap model) {
        model.addAttribute("conteudo", "/cliente/add");
        p.setCdCliente((int) (repo.count() + 1));
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("formapagtos", getFormapagtos());
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Cliente cliente, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/cliente/add");
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("formapagtos", getFormapagtos());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (repo.findById(cliente.getCdCliente()) != null) {
            cliente.setCdCliente((int) (repo.count() + 1));
        }
        repo.save(cliente);
        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/cliente/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Cliente cliente, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/cliente/add");
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("formapagtos", getFormapagtos());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(cliente);
        attrib.addFlashAttribute("message", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/cliente/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Cliente e = repo.findById(id).get();
        model.addAttribute("cliente", e);
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("formapagtos", getFormapagtos());
        model.addAttribute("conteudo", "/cliente/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        repo.deleteById(id);
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/cliente/";
    }

    @ModelAttribute
    public List<Endereco> getEnderecos() {
        return enderecos.findAll();
    }

    @ModelAttribute
    public List<Formapagto> getFormapagtos() {
        return formapagtos.findAll();
    }
}