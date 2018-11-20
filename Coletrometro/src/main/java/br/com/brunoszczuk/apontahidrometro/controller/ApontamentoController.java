/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FotoapontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bruno
 */
@Controller
@RequestMapping("apontamento")
public class ApontamentoController {

    @Autowired
    ApontamentoRepository repo;

    @Autowired
    EquipamentoRepository equipamentos;

    @Autowired
    ContratoRepository contratos;

    @Autowired
    UsuarioRepository usuarios;

    @Autowired
    FotoapontamentoRepository fotoapontamento;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("apontamentos", repo.findAll(new Sort(Sort.Direction.ASC, "cdApontamento")));
        model.addAttribute("conteudo", "/apontamento/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Apontamento p, ModelMap model) {
        model.addAttribute("conteudo", "/apontamento/add");
        p.setDtInclusao(new Date());
        model = getModel(model);
        p.setUsuario(usuarios.findByIgnoreCaseNickUsuario(SecurityContextHolder.getContext().getAuthentication().getName()));
        return new ModelAndView("layout", model);
    }

    @PostMapping(value = "/save")
    public ModelAndView save(@Valid Apontamento apontamento, BindingResult result, RedirectAttributes attrib, ModelMap model) throws IOException {

        model.addAttribute("conteudo", "/apontamento/add");
        apontamento.setUsuario(usuarios.findByIgnoreCaseNickUsuario(apontamento.getUsuario().getNickUsuario()));
        if (result.hasErrors()) {
            return new ModelAndView("layout", getModel(model));
        }
        if(apontamento.getFotoapontamento().getFile().isEmpty()){
            attrib.addFlashAttribute("message", bundle.getString("message.fotoapontamento.ftapontamento"));
            return new ModelAndView("redirect:/apontamento/add");
        }
        apontamento.setDtInclusao(new Date());
        apontamento.getFotoapontamento().setFtApontamento(apontamento.getFotoapontamento().getFile().getBytes());

        fotoapontamento.save(apontamento.getFotoapontamento());
        repo.save(apontamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/apontamento/");
    }

    @PostMapping(value = "/update")

    public ModelAndView update(@Valid Apontamento apontamento, BindingResult result, RedirectAttributes attrib, ModelMap model) throws IOException {
        model.addAttribute("conteudo", "/apontamento/add");
        //model.addAttribute("apontamento", repo.findById(apontamento.getCdApontamento()).get());
        apontamento.setUsuario(usuarios.findByIgnoreCaseNickUsuario(apontamento.getUsuario().getNickUsuario()));
        if (result.hasErrors()) {
            return new ModelAndView("layout", getModel(model));
        }
        if (apontamento.getFotoapontamento().getFile().getBytes().length == 0) {
            apontamento.setFotoapontamento(fotoapontamento.findByApontamento(apontamento.getCdApontamento()));
        } else {
            apontamento.getFotoapontamento().setFtApontamento(apontamento.getFotoapontamento().getFile().getBytes());
        }

        fotoapontamento.save(apontamento.getFotoapontamento());
        repo.save(apontamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/apontamento/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) throws IOException {
        Apontamento e = repo.findById(id).get();
        model.addAttribute("apontamento", e);
        model.addAttribute("conteudo", "/apontamento/add");
        return new ModelAndView("layout", getModel(model));
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            int idfoto = repo.findById(id).get().getFotoapontamento().getCdFoto();
            repo.deleteById(id);
            fotoapontamento.deleteById(idfoto);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbexistedependencia"));
        }
        return "redirect:/apontamento/";
    }

    @ModelAttribute
    public List<Equipamento> getEquipamentos() {
        return equipamentos.findByContratoAtivo();
    }

    @ModelAttribute
    public List<Contrato> getContratos() {
        return contratos.findAll();
    }

    private ModelMap getModel(ModelMap model) {
        model.addAttribute("equipamentos", getEquipamentos());
        model.addAttribute("contratos", getContratos());
        return model;
    }
}
