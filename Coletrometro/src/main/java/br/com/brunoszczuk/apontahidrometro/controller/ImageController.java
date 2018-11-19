/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Fotoapontamento;
import br.com.brunoszczuk.apontahidrometro.repository.FotoapontamentoRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Bruno
 */
@Controller
@RequestMapping("/fotoapontamento")
public class ImageController {

    @Autowired
    private FotoapontamentoRepository repo;

    @RequestMapping(value = "/gerafoto", method = RequestMethod.GET)
    public void showImage(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        if (repo.existsById(id)) {
            Fotoapontamento foto = repo.findById(id).get();
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(foto.getFtApontamento());
            response.getOutputStream().close();
        }

    }
}
