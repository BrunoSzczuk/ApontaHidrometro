/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Competencia;
import br.com.brunoszczuk.apontahidrometro.entities.Contareceber;
import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Fechamentoapontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Itemcondicaopagto;
import br.com.brunoszczuk.apontahidrometro.entities.Itemfechamento;
import br.com.brunoszczuk.apontahidrometro.entities.ItemfechamentoId;
import br.com.brunoszczuk.apontahidrometro.entities.Tabpreco;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.CompetenciaRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContareceberRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FechamentoapontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ItemfechamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.TabprecoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
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

    @Autowired
    ItemfechamentoRepository itemFechamentos;

    @Autowired
    ContareceberRepository contareceberRepository;

    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

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
        model.addAttribute("tabprecos", getTabpreco());
        return new ModelAndView("layout", model);
    }

    @PostMapping(value = "/save", params = "part1")
    private ModelAndView addPart1(Fechamentoapontamento p, ModelMap model, BindingResult result, RedirectAttributes attrib) {
        if (p.getContrato() == null) {
            attrib.addFlashAttribute("message", bundle.getString("lbinformecontrato"));
            return new ModelAndView("redirect:/fechamentoapontamento/add");
        }
        if (p.getCompetencia() == null) {
            attrib.addFlashAttribute("message", bundle.getString("lbinformecompetencia"));
            return new ModelAndView("redirect:/fechamentoapontamento/add");
        }
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        Contrato c = contratos.findById(p.getContrato().getNrContrato()).get();
        model.addAttribute("contratos", c);
        model.addAttribute("apontamentos", getApontamentos(c));
        model.addAttribute("tabprecos", getTabpreco());
        model.addAttribute("competencias", getCompetencias());
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(Fechamentoapontamento fechamentoapontamento, @RequestParam("apontamentosselecinados") List<String> apontamentos, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/fechamentoapontamento/save?part1");
        }
        fechamentoapontamento.setDtInclusao(new Date());
        repo.save(fechamentoapontamento);

        HashSet<Itemfechamento> lista = new HashSet();
        //Pegar o ID gerado do fechamento salvo
        Fechamentoapontamento ultimo = repo.findTopByOrderByCdFechamentoDesc();

        //Usando Programação funcional - JDK 8
        apontamentos.forEach(obj -> lista.add(new Itemfechamento(new ItemfechamentoId(ultimo.getCdFechamento(), this.apontamentos.findById(Integer.valueOf(obj)).get().getCdApontamento()),
                this.apontamentos.findById(Integer.valueOf(obj)).get(), fechamentoapontamento)));
        itemFechamentos.saveAll(lista);
        for (Itemfechamento item : lista) {
            item.getApontamento().setStFechado(true);
            this.apontamentos.save(item.getApontamento());
        }
        //lista.forEach(obj -> obj.getApontamento().setStFechado(true));
        geraTituloFinanceiro(fechamentoapontamento);
        //this.apontamentos.saveAll(lista.getClass());
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/fechamentoapontamento/");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            //repo.deleteById(new FechamentoapontamentoId); montar o deletebyid no repository
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbregistroexistente"));
        }
        return "redirect:/fechamentoapontamento/";
    }

    @ModelAttribute
    public List<Contrato> getContratos() {
        return contratos.findByContratoAtivo();
    }

    @ModelAttribute
    public List<Competencia> getCompetencias() {
        return competencias.findAll();
    }

    public Tabpreco getTabpreco() {
        return tabprecos.findByTabelaVigente(LocalDate.now());
    }

    public List<Apontamento> getApontamentos(Contrato c) {
        return apontamentos.findByEquipamento(c.getUnidadeconsumidora().getEquipamento(), new Sort(Sort.Direction.ASC, "dtInclusao"));
    }

    private void geraTituloFinanceiro(Fechamentoapontamento fechamentoapontamento) {
        fechamentoapontamento = repo.findById(fechamentoapontamento.getCdFechamento()).get();
        
        int qtdapontamento = fechamentoapontamento.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).max().getAsInt()
                - fechamentoapontamento.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).min().getAsInt();
        float vltotal =0;
        vltotal += fechamentoapontamento.getTabpreco().getVlTaxaadm().floatValue();
        vltotal += fechamentoapontamento.getTabpreco().getVlMetrocubico().floatValue() * qtdapontamento;
        Date data = fechamentoapontamento.getDtInclusao();
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        fechamentoapontamento.setVlFechamento(vltotal);
        repo.save(fechamentoapontamento);
        for (Itemcondicaopagto item : fechamentoapontamento.getContrato().getCondicaopagto().getItemcondicaopagtos()) {
            c.add(Calendar.DATE, item.getQtDias());
            contareceberRepository.save(new Contareceber(0, fechamentoapontamento.getContrato().getCliente(), fechamentoapontamento.getContrato().getCliente().getFormapagto(),
                    fechamentoapontamento.getCdFechamento() + "", true,new BigDecimal(0), fechamentoapontamento.getDtInclusao(), false, new BigDecimal(item.getPcQuota() / vltotal *100), new BigDecimal(0), c.getTime(),fechamentoapontamento.getCdFechamento() +""));
        }
    }
}
