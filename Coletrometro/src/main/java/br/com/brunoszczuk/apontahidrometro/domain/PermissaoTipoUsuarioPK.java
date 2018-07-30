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
@Embeddable
public class PermissaoTipoUsuarioPK implements Serializable{
    @JoinColumn(name = "cd_tipousuario", referencedColumnName = "cd_tipo")
    TipoUsuario tipoUsuario;
    
    @JoinColumn(name = "cd_permissao", referencedColumnName = "cd_permissao")
    Permissao permissao;

    public PermissaoTipoUsuarioPK() {
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    @Override
    public String toString() {
        return "PermissaoTipoUsuarioPK{" + "tipoUsuario=" + tipoUsuario + ", permissao=" + permissao + '}';
    }
    
    
}
