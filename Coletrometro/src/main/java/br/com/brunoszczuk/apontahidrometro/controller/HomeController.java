package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.repository.ApontamentoRepository;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    ApontamentoRepository apontamentoRepository;

    @GetMapping("/")
    public ModelAndView home(ModelMap model) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -15);
        return new ModelAndView("layout", RelatorioController.relatorioConsumo(model, apontamentoRepository.findBydtInclusaoBetween(cal.getTime(), new Date())));
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
