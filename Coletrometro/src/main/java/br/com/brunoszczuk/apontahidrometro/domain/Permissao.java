/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author bruno.szczuk
 */
@Entity
@Table(name = "permissao")
public class Permissao implements Serializable{
    @Id
    @Column(name = "cd_permissao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cdPermissao;
    
    @Column(name = "nm_rotina")
    private String nmRotina;

    public int getCdPermissao() {
        return cdPermissao;
    }

    public void setCdPermissao(int cdPermissao) {
        this.cdPermissao = cdPermissao;
    }

    public String getNmRotina() {
        return nmRotina;
    }

    public void setNmRotina(String nmRotina) {
        this.nmRotina = nmRotina;
    }

    @Override
    public String toString() {
        return "Permissao{" + "cdPermissao=" + cdPermissao + ", nmRotina=" + nmRotina + '}';
    }

    public Permissao() {
    }
    
}
