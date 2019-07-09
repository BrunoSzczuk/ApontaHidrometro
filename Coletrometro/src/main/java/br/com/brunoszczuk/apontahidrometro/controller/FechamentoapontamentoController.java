/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Competencia;
import br.com.brunoszczuk.apontahidrometro.entities.Condicaopagto;
import br.com.brunoszczuk.apontahidrometro.entities.Contareceber;
import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import br.com.brunoszczuk.apontahidrometro.entities.Fechamentoapontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Itemcondicaopagto;
import br.com.brunoszczuk.apontahidrometro.entities.Itemfechamento;
import br.com.brunoszczuk.apontahidrometro.entities.ItemfechamentoId;
import br.com.brunoszczuk.apontahidrometro.entities.Tabpreco;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.CompetenciaRepository;
import br.com.brunoszczuk.apontahidrometro.repository.CondicaopagtoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContareceberRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.EquipamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.FechamentoapontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ItemfechamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.TabprecoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    EquipamentoRepository equipamentoRepository;
    @Autowired
    CondicaopagtoRepository condicaopagtoRepository;
    
    ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping("/")
    private ModelAndView home(ModelMap model) {
        model.addAttribute("fechamentoapontamentos", repo.findAll(new Sort(Sort.Direction.ASC, "cdFechamento")));
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
        Contrato c = contratos.findById(p.getContrato().getNrContrato()).get();
        p.setCompetencia(competencias.findById(p.getCompetencia().getCdCompetencia()).get());
        if (getApontamentos(c, p.getCompetencia()).isEmpty()) {
            attrib.addFlashAttribute("message", bundle.getString("lbnaotemapontamento"));
            return new ModelAndView("redirect:/fechamentoapontamento/add");
        }

        model.addAttribute("conteudo", "/fechamentoapontamento/add");

        model.addAttribute("contratos", c);
        model.addAttribute("fechamentoapontamento", p);
        model.addAttribute("apontamentos", getApontamentos(c, p.getCompetencia()));
        model.addAttribute("tabprecos", getTabpreco());
        model.addAttribute("competencias", getCompetencias());
        return new ModelAndView("layout", model);
    }

    @PostMapping("/save")
    public ModelAndView save(Fechamentoapontamento fechamentoapontamento, @RequestParam(value= "apontamentosselecinados",required = false) List<String> apontamentos, BindingResult result, RedirectAttributes attrib, ModelMap model) {
        model.addAttribute("conteudo", "/fechamentoapontamento/add");
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/fechamentoapontamento/save?part1");
        }
        if(apontamentos == null){
            attrib.addFlashAttribute("message", bundle.getString("message.fechamentoapontamento.semapontamentos"));
            return new ModelAndView("redirect:/fechamentoapontamento/add");
        }
        //Select no objeto pra montar ele completo
        Contrato c = contratos.findById(fechamentoapontamento.getContrato().getNrContrato()).get();
        Competencia competencia =competencias.findById(fechamentoapontamento.getCompetencia().getCdCompetencia()).get();
        if (this.apontamentos.hasApontamentosAnteriores(c.getUnidadeconsumidora().getEquipamento().getCdEquipamento(), competencia.getDtInicio())) {
            attrib.addFlashAttribute("message", bundle.getString("message.fechamentoapontamento.apontamentoanteriores"));
            return new ModelAndView("redirect:/fechamentoapontamento/add");
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
        fechamentoapontamento.setItemfechamentos(lista);
        geraTituloFinanceiro(fechamentoapontamento);
        attrib.addFlashAttribute("message", bundle.getString("lbregistroinseridocomsucesso"));
        return new ModelAndView("redirect:/fechamentoapontamento/");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, RedirectAttributes attrib) {
        try {
            Fechamentoapontamento fechamentoapontamento = repo.findById(id).get();
            apontamentos.updateApontamentoSetStatus(id, false);
            int qtdapontada = fechamentoapontamento.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).sum();
            equipamentoRepository.updateEquipamentoSetContAtual(fechamentoapontamento.getItemfechamentos().iterator().next().getApontamento().getEquipamento().getCdEquipamento(),
                    qtdapontada);
            itemFechamentos.deleteItemfechamentoByCdFechamento(id);
            contareceberRepository.deleteContareceberByCdFechamento(id);
            repo.deleteById(id);
            attrib.addFlashAttribute("message", bundle.getString("lbregistroremovidocomsucesso"));

        } catch (DataIntegrityViolationException ex) {
            attrib.addFlashAttribute("errorMessage", bundle.getString("lbexistedependencia"));
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

    public List<Apontamento> getApontamentos(Contrato c, Competencia competencia) {
        return apontamentos.findByEquipamentoandContratoAtivo(c.getUnidadeconsumidora().getEquipamento(), competencia.getDtInicio(),
                competencia.getDtFim());
    }

    private void geraTituloFinanceiro(Fechamentoapontamento fechamentoapontamento) {

        try {
            //Recarregar os objetos do join
            Fechamentoapontamento f = repo.findById(fechamentoapontamento.getCdFechamento()).get();
            //Usando Programação funcional com Stream - JDK 8
            int qtdapontamento = f.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).max().getAsInt()
                    - f.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).min().getAsInt();
            float vltotal = 0;
            Tabpreco tabpreco = tabprecos.findById(f.getTabpreco().getCdTabpreco()).get();
            vltotal += tabpreco.getVlTaxaadm().floatValue();
            vltotal += tabpreco.getVlMetrocubico().floatValue() * qtdapontamento;
            Date data = f.getDtInclusao();
            Calendar c = Calendar.getInstance();
            c.setTime(data);

            f.setVlFechamento(vltotal);
            repo.save(f);
            Contrato contrato = contratos.findById(f.getContrato().getNrContrato()).get();
            Condicaopagto condicaopagto = condicaopagtoRepository.findById(contrato.getCondicaopagto().getCdCondicaopagto()).get();
            for (Itemcondicaopagto item : condicaopagto.getItemcondicaopagtos()) {
                c.add(Calendar.DATE, item.getQtDias());
                contareceberRepository.save(new Contareceber(0, contrato.getUnidadeconsumidora().getCliente(), contrato.getUnidadeconsumidora().getCliente().getFormapagto(),
                        f.getCdFechamento() + "", f.getDtInclusao(), false, new BigDecimal(item.getPcQuota() / vltotal * 100), c.getTime(), f.getCdFechamento()));
            }
            Equipamento eq = equipamentoRepository.findById(contrato.getUnidadeconsumidora().getEquipamento().getCdEquipamento()).get();
            eq.setContAtual(f.getItemfechamentos().stream().mapToInt(obj -> obj.getApontamento().getContApontado()).max().getAsInt());
            equipamentoRepository.save(eq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
