/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Permissaotipousuario;
import br.com.brunoszczuk.apontahidrometro.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
/**
 *
 * @author Bruno
 */
@Component
public class PermissaotipousuarioConverter implements Converter<Integer, Permissaotipousuario> {

    @Autowired
    PermissaoRepository repo;
    
    @Override
    public Permissaotipousuario convert(Integer s) {
        return new Permissaotipousuario(0,repo.findById(s).get(), null, null, null);
    }

 
}
