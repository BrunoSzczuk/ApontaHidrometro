/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import br.com.brunoszczuk.apontahidrometro.repository.ContratoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author bruno.szczuk
 */
@Controller
@RequestMapping("/canvasjschart")
public class CanvasjsChartController {

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    ApontamentoRepository apontamentoRepository;

    static Map<Object, Object> map = null;
    static List<List<Map<Object, Object>>> list = new ArrayList<List<Map<Object, Object>>>();
    static List<Map<Object,Object>> dataPoints1 = new ArrayList<Map<Object,Object>>();

    @RequestMapping(method = RequestMethod.GET)
    public String springMVC(ModelMap modelMap) {
        List<Apontamento> apontamentos = apontamentoRepository.findAll();
        apontamentos.forEach(obj -> {
            map.put("x", obj.getDtInclusao());
            map.put("y", obj.getContApontado());
            dataPoints1.add(map);
            
        });
        list.add(dataPoints1);
        List<List<Map<Object, Object>>> canvasjsDataList = list;
        modelMap.addAttribute("dataPointsList", canvasjsDataList);
        return "chart";
    }
}
