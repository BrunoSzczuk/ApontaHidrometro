package br.com.brunoszczuk.apontahidrometro.domain;
// Generated 31/07/2018 18:08:45 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PermissaotipousuarioId generated by hbm2java
 */
@Embeddable
public class PermissaotipousuarioId implements java.io.Serializable {

    private String cdTipousuario;
    private int cdPermissao;

    public PermissaotipousuarioId() {
    }

    public PermissaotipousuarioId(String cdTipousuario, int cdPermissao) {
        this.cdTipousuario = cdTipousuario;
        this.cdPermissao = cdPermissao;
    }

    @Column(name = "cd_tipousuario", nullable = false, length = 30)
    public String getCdTipousuario() {
        return this.cdTipousuario;
    }

    public void setCdTipousuario(String cdTipousuario) {
        this.cdTipousuario = cdTipousuario;
    }

    @Column(name = "cd_permissao", nullable = false)
    public int getCdPermissao() {
        return this.cdPermissao;
    }

    public void setCdPermissao(int cdPermissao) {
        this.cdPermissao = cdPermissao;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof PermissaotipousuarioId)) {
            return false;
        }
        PermissaotipousuarioId castOther = (PermissaotipousuarioId) other;

        return ((this.getCdTipousuario() == castOther.getCdTipousuario()) || (this.getCdTipousuario() != null && castOther.getCdTipousuario() != null && this.getCdTipousuario().equals(castOther.getCdTipousuario())))
                && (this.getCdPermissao() == castOther.getCdPermissao());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getCdTipousuario() == null ? 0 : this.getCdTipousuario().hashCode());
        result = 37 * result + this.getCdPermissao();
        return result;
    }

    @Override
    public String toString() {
        return "PermissaotipousuarioId{" + "cdTipousuario=" + cdTipousuario + ", cdPermissao=" + cdPermissao + '}';
    }
    
    

}
