/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Unidadeconsumidora;
import br.com.brunoszczuk.apontahidrometro.entities.Cliente;
import br.com.brunoszczuk.apontahidrometro.entities.Endereco;
import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import br.com.brunoszczuk.apontahidrometro.repository.ClienteRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EnderecoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UnidadeconsumidoraRepository;
import java.util.Date;
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
@RequestMapping("unddconsumidora")
public class UnidadeconsumidoraController {

    @Autowired
    UnidadeconsumidoraRepository repo;

    @Autowired
    ClienteRepository clientes;

    @Autowired
    EquipamentoRepository equipamentos;
    
    @Autowired
    EnderecoRepository enderecos;

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("unidadesonsumidoras", repo.findAll(new Sort(Sort.Direction.ASC, "cdUnidadeconsumidora")));
        model.addAttribute("conteudo", "/unddconsumidora/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Unidadeconsumidora p, ModelMap model) {
        p.setCdUnidadeconsumidora((int) repo.count() + 1);
        model.addAttribute("conteudo", "/unddconsumidora/add");
        model.addAttribute("clientes", getClientes());
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("equipamentos", getEquipamentos());
        return new ModelAndView("layout", "conteudo", "/unddconsumidora/add");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Unidadeconsumidora unidadeConsumidora, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/unddconsumidora/add");
        model.addAttribute("clientes", getClientes());
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("equipamentos", getEquipamentos());
        if (unidadeConsumidora.getDtInclusao() == null){
            unidadeConsumidora.setDtInclusao(new Date());
        }
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (repo.findById(unidadeConsumidora.getCdUnidadeconsumidora()) != null) {
            unidadeConsumidora.setCdUnidadeconsumidora((int) (repo.count() + 1));
        }
        repo.save(unidadeConsumidora);
        attrib.addFlashAttribute("message", "Registro inserido com sucesso.");
        return new ModelAndView("redirect:/unddconsumidora/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Unidadeconsumidora unidadeConsumidora, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/unddconsumidora/add");
        model.addAttribute("clientes", getClientes());
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("equipamentos", getEquipamentos());
        if (unidadeConsumidora.getDtInclusao() == null){
            unidadeConsumidora.setDtInclusao(new Date());
        }
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(unidadeConsumidora);
        attrib.addFlashAttribute("message", "Registro alterado com sucesso.");
        return new ModelAndView("redirect:/unddconsumidora/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Unidadeconsumidora e = repo.findById(id).get();
        model.addAttribute("unidadeconsumidora", e);
        model.addAttribute("clientes", getClientes());
        model.addAttribute("enderecos", getEnderecos());
        model.addAttribute("equipamentos", getEquipamentos());
        model.addAttribute("conteudo", "/unddconsumidora/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        repo.deleteById(id);
        attrib.addFlashAttribute("message", "Registro removido com sucesso.");
        return "redirect:/unddconsumidora/";
    }

    @ModelAttribute
    public List<Cliente> getClientes() {
        return clientes.findAll();
    }

    @ModelAttribute
    public List<Equipamento> getEquipamentos() {
        return equipamentos.findAll();
    }
    
    @ModelAttribute
    public List<Endereco> getEnderecos() {
        return enderecos.findAll();
    }
}
