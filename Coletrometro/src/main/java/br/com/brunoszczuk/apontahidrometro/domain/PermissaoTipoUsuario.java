/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author bruno.szczuk
 */
@Entity
@Table(name = "permissaotipousuario")
public class PermissaoTipoUsuario implements Serializable {

    @EmbeddedId
    PermissaoTipoUsuarioPK pk;

    @Column(name = "dt_inclusao")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dtInclusao;

    @Column(name = "dt_ultimaatualizacao")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dtUltimaAtualizacao;

    public PermissaoTipoUsuario() {
    }

    public PermissaoTipoUsuarioPK getPk() {
        return pk;
    }

    public void setPk(PermissaoTipoUsuarioPK pk) {
        this.pk = pk;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public Date getDtUltimaAtualizacao() {
        return dtUltimaAtualizacao;
    }

    public void setDtUltimaAtualizacao(Date dtUltimaAtualizacao) {
        this.dtUltimaAtualizacao = dtUltimaAtualizacao;
    }

    @Override
    public String toString() {
        return "PermissaoTipoUsuario{" + "pk=" + pk + ", dtInclusao=" + dtInclusao + ", dtUltimaAtualizacao=" + dtUltimaAtualizacao + '}';
    }
    
}
