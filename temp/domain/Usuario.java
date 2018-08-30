/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author bruno.szczuk
 */
public class Usuario implements Serializable {

    @Id
    @Column(name = "nick_usuario")
    @Size(max = 60, min = 4)
    @NotBlank(message = "É necessário informar um nick")
    private String nickUsuario;

    @Column(name = "sn_usuario")
    @Size(max = 80, min = 5)
    @NotBlank(message = "É necessário informar uma senha")
    private String snUsuario;

    @Column(name = "ds_email")
    @Email
    @NotBlank(message = "É necessário informar um e-mail")
    private String dsEmail;

    @Column(name = "dt_inclusao", columnDefinition = "DATE")
    @NotNull(message = "Informe a data de inclusão.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dtInclusao;

    @Column(name = "dt_ultimologin", columnDefinition = "DATE")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dtUltimoLogin;

    @Column(name = "st_ativo", length = 20)
    @Enumerated(EnumType.STRING)
    private Status stAtivo;

    @OneToOne
    @JoinColumn(name = "cd_tipousuario", referencedColumnName = "cd_tipo")
    @NotNull(message = "É necessário informar o tipo do usuário")
    private TipoUsuario tipoUsuario;

    private List<Permissao> permissoes;

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.nickUsuario);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nickUsuario, other.nickUsuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nickUsuario=" + nickUsuario + ", snUsuario=" + snUsuario + ", dsEmail=" + dsEmail + ", dtInclusao=" + dtInclusao + ", dtUltimoLogin=" + dtUltimoLogin + ", stAtivo=" + stAtivo + ", tipoUsuario=" + tipoUsuario + '}';
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    public String getSnUsuario() {
        return snUsuario;
    }

    public void setSnUsuario(String snUsuario) {
        this.snUsuario = snUsuario;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public Date getDtUltimoLogin() {
        return dtUltimoLogin;
    }

    public void setDtUltimoLogin(Date dtUltimoLogin) {
        this.dtUltimoLogin = dtUltimoLogin;
    }

    public Status getStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Status stAtivo) {
        this.stAtivo = stAtivo;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {
    }

}
