/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.validation.FieldError;

/**
 *
 * @author bruno.szczuk
 */
public class ValidatorUtil {

    static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    static final Validator VALIDATOR = FACTORY.getValidator();

    /**
     *
     * @param objeto
     * @param campo
     * @return erro padrão colocado na anotação do objeto
     */
    public static FieldError addErrorFieldDefault(Object objeto, String campo) {
        return new FieldError(objeto.getClass().getName(), campo, VALIDATOR.validateProperty(objeto, campo).iterator().next().getMessage());
    }

    /**
     *
     * @param objeto
     * @param campo
     * @param msg
     * @return erro do parâmetro
     */
    public static FieldError addErrorField(Object objeto, String campo, String msg) {
        return new FieldError(objeto.getClass().getName(), campo, msg);
    }

    public static boolean hasErrorField(Object objeto, String campo) {
        return !VALIDATOR.validateProperty(objeto, campo).isEmpty();
    }
}
