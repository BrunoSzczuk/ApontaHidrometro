/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author bruno.szczuk
 */
public class TipoUsuario implements Serializable{
    @Id
    @Column(name = "cd_tipo")
    private String cdTipo;
    
    @Column(name = "ds_tipo")
    @Size(max = 20,min = 2)
    @NotBlank(message = "É necessário informar uma descrição")
    private String dsTipo;
    
    @Column(name = "st_ativo", length = 20)
    @Enumerated(EnumType.STRING)
    private Status stAtivo;

    public TipoUsuario() {
    }

    public String getCdTipo() {
        return cdTipo;
    }

    public void setCdTipo(String cdTipo) {
        this.cdTipo = cdTipo;
    }

    public String getDsTipo() {
        return dsTipo;
    }

    public void setDsTipo(String dsTipo) {
        this.dsTipo = dsTipo;
    }

    public Status getStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Status stAtivo) {
        this.stAtivo = stAtivo;
    }

    @Override
    public String toString() {
        return "TipoUsuario{" + "cdTipo=" + cdTipo + ", dsTipo=" + dsTipo + ", stAtivo=" + stAtivo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.cdTipo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoUsuario other = (TipoUsuario) obj;
        if (!Objects.equals(this.cdTipo, other.cdTipo)) {
            return false;
        }
        return true;
    }
    
}
