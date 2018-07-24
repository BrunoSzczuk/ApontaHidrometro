/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

/**
 *
 * @author bruno.szczuk
 */
public enum Status {

    ATIVO('@'), INATIVO('*');

    private char desc;

    Status(char desc) {
        this.desc = desc;
    }

    public char getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
