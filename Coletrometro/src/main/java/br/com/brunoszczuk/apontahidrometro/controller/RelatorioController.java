/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UnidadeconsumidoraRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("relatorio")
public class RelatorioController {

    static Map<Object, Object> map = null;
    static List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
    static List<Map<Object, Object>> dataPoints1 = new ArrayList<Map<Object, Object>>();
    @Autowired
    ApontamentoRepository apontamentoRepository;
    
    @Autowired
    UnidadeconsumidoraRepository unidadeconsumidoraRepository;
    public static ModelMap relatorioConsumo(ModelMap model, List<Apontamento> apontamentos) {
        model.addAttribute("conteudo", "/relatorio");
        list.clear();
        dataPoints1.clear();
        apontamentos.forEach(obj -> {
            map = new HashMap<Object, Object>();
            map.put("x", obj.getDtInclusao().getTime());
            map.put("y", obj.getContApontado() - obj.getContAnterior());
            dataPoints1.add(map);

        });
        list.add(dataPoints1);
        List<List<Map<Object, Object>>> canvasjsDataList = list;
        model.addAttribute("dtemissao", new Date());
        model.addAttribute("dataPointsList", canvasjsDataList);
        return model;
    }
    
    
    @GetMapping("/consumo")
    public ModelAndView consumo(Relatorioconsumo r, ModelMap model){
        model.addAttribute("conteudo", "/relatorio/comparativoconsumo");
        model.addAttribute("unidadeconsumidoras", unidadeconsumidoraRepository.findAll());
        return new ModelAndView("layout", model);
    }
    @PostMapping("/consumo/gerar")
    public ModelAndView gerar(@Valid Relatorioconsumo relatorio, BindingResult result, RedirectAttributes attrib, ModelMap model){
        model.addAttribute("conteudo", "/relatorio/comparativoconsumo");
        model.addAttribute("unidadeconsumidoras", unidadeconsumidoraRepository.findAll());
        if (result.hasErrors()) {
            return new ModelAndView("layout", model);
        }
        
        return new ModelAndView("layout", relatorioConsumo(model, apontamentoRepository.findBydtInclusaoBetweenAndUnidadeconsumidora(
                relatorio.getdtPeriodoinicial(), relatorio.getdtPeriodofinal(), relatorio.getUnidadeconsumidora())));
    }
    
}
