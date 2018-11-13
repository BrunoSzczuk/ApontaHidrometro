/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Competencia;
import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Fechamentoapontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Tabpreco;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.CompetenciaRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FechamentoapontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.TabprecoRepository;
import java.time.LocalDate;
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
@RequestMapping("fechamentoapontamento")
public class FechamentoapontamentoController {

    @Autowired
    FechamentoapontamentoRepository repo;

    @Autowired
    ApontamentoRepository apontamentos;

    @Autowired
    ContratoRepository contratos;
    
    @Autowired
    TabprecoRepository tabprecos;
    
    @Autowired
    CompetenciaRepository competencias;

    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("fechamentoapontamentos", repo.findAll(new Sort(Sort.Direction.ASC, "cdFechamentoapontamento")));
        model.addAttribute("conteudo", "/fechamentoapontamento/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Fechamentoapontamento p, ModelMap model) {
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        model.addAttribute("competencias", getCompetencias());
        model.addAttribute("contratos", getContratos());
        model.addAttribute("tabprecos",getTabpreco());
        return new ModelAndView("layout", model);
    }
    
    
    @PostMapping(value = {"/save","/update"}, params = "part1")
    private ModelAndView addPart1(Fechamentoapontamento p, ModelMap model) {
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        model.addAttribute("contratos", getContratos());
        model.addAttribute("apontamentos", getApontamentos(p.getContrato()));
        p.setTabpreco(getTabpreco());
        model.addAttribute("competencias", getCompetencias());
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Fechamentoapontamento fechamentoapontamento, BindingResult result, RedirectAttributes attrib, ModelMap model) {

        model.addAttribute("conteudo", "/fechamentoapontamento/add");
      //  model.addAttribute("enderecos", getEnderecos());
     //   model.addAttribute("formapagtos", getFormapagtos());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        repo.save(fechamentoapontamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/fechamentoapontamento/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Fechamentoapontamento fechamentoapontamento, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
    //    model.addAttribute("enderecos", getEnderecos());
    //    model.addAttribute("formapagtos", getFormapagtos());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }

        repo.save(fechamentoapontamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/fechamentoapontamento/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Fechamentoapontamento e = repo.findById(id).get();
        model.addAttribute("fechamentoapontamento", e);
   //     model.addAttribute("enderecos", getEnderecos());
   //     model.addAttribute("formapagtos", getFormapagtos());
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));
            
        }catch(DataIntegrityViolationException ex){
            attrib.addFlashAttribute("errorMessage",bundle.getString("lbregistroexistente"));
        }
        return "redirect:/fechamentoapontamento/";
    }

    @ModelAttribute
    public List<Contrato> getContratos() {
        return contratos.findByContratoAtivo();
    }
    
    @ModelAttribute
    public List<Competencia> getCompetencias(){
        return competencias.findAll();
    }
    
    public Tabpreco getTabpreco(){
        return tabprecos.findByTabelaVigente(LocalDate.now());
    }
    
    
    public List<Apontamento> getApontamentos(Contrato c){
        return apontamentos.findByEquipamento(c.getUnidadeconsumidora().getEquipamento());
    }
}
