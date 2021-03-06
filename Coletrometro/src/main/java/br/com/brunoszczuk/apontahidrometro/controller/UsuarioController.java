/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Usuario;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.brunoszczuk.apontahidrometro.repository.TipousuarioRepository;
import br.com.brunoszczuk.apontahidrometro.util.ValidatorUtil;

/**
 *
 * @author bruno
 */
@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    TipousuarioRepository tipousuarios;
    ResourceBundle bundle = ResourceBundle.getBundle("messages",Locale.getDefault());

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("usuarios", repo.findAll(new Sort(Sort.Direction.ASC, "cdUsuario")));
        model.addAttribute("conteudo", "/usuario/list");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/add")
    private ModelAndView add(Usuario p, ModelMap model) {
        p.setCdUsuario((int) repo.count() + 1);
        model.addAttribute("conteudo", "/usuario/add");
        model.addAttribute("tipousuarios", getTipousuarios());
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Usuario usuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/usuario/add");
        model.addAttribute("tipousuarios", getTipousuarios());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (repo.findById(usuario.getCdUsuario()) != null) {
            usuario.setCdUsuario((int) (repo.count() + 1));
        }
        usuario.setSnUsuario(encoder.encode(usuario.getSnUsuario()));
        usuario.setDtInclusao(new Date());
        repo.save(usuario);

        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/usuario/");
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Usuario usuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/usuario/add");
        model.addAttribute("tipousuarios", getTipousuarios());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        if (usuario.getDtInclusao() == null) {
            usuario.setDtInclusao(new Date());
        }
        if (usuario.getSnUsuario() != null) {
            usuario.setSnUsuario(encoder.encode(usuario.getSnUsuario()));
        }
        usuario.setSnUsuario(repo.findById(usuario.getCdUsuario()).get().getSnUsuario());
        repo.save(usuario);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/usuario/");
    }

    @GetMapping("/update/{id}")
    public ModelAndView preUpdate(@PathVariable("id") int id, ModelMap model) {
        Usuario e = repo.findById(id).get();
        model.addAttribute("usuario", e);
        model.addAttribute("tipousuarios", getTipousuarios());
        model.addAttribute("conteudo", "/usuario/add");
        return new ModelAndView("layout", model);
    }

    @GetMapping("/password/{id}")
    public ModelAndView password(@PathVariable("id") int id, ModelMap model) {
        Usuario e = repo.findById(id).get();
        model.addAttribute("usuario", e);
        model.addAttribute("conteudo", "/usuario/password");
        return new ModelAndView("layout", model);
    }

    @PostMapping("/password/save")
    public ModelAndView passwordSave(Usuario usuario, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/usuario/password");
        if (ValidatorUtil.hasErrorField(usuario, "snUsuario")) {
            result.addError(ValidatorUtil.addErrorFieldDefault(usuario, "snUsuario"));
            return new ModelAndView("layout", model);
        }
        Usuario outro = repo.findById(usuario.getCdUsuario()).get();
        outro.setSnUsuario(new BCryptPasswordEncoder().encode(usuario.getSnUsuario()));
        repo.save(outro);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroalteradocomsucesso"));
        return new ModelAndView("redirect:/usuario/");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));
            
        }catch(DataIntegrityViolationException ex){
            attrib.addFlashAttribute("errorMessage",bundle.getString("lbexistedependencia"));
        }
        return "redirect:/usuario/";
    }

    @ModelAttribute
    private List<Tipousuario> getTipousuarios() {
        return tipousuarios.findAll();
    }
}
