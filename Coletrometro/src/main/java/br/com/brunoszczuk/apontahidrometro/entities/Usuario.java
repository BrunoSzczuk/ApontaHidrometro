package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 29/08/2018 23:02:23 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name="usuario"
    ,schema="public"
)
public class Usuario  implements java.io.Serializable {


     private int cdUsuario;
     private Tipousuario tipousuario;
     private String nickUsuario;
     private String snUsuario;
     private String dsEmail;
     private Date dtUltimologin;
     private boolean stAtivo;
     private Date dtInclusao;
     private Set<Apontamento> apontamentos = new HashSet<Apontamento>(0);
     private Set<Contratousuario> contratousuarios = new HashSet<Contratousuario>(0);

    public Usuario() {
    }

	
    public Usuario(int cdUsuario, Tipousuario tipousuario, String nickUsuario, String snUsuario, String dsEmail, boolean stAtivo, Date dtInclusao) {
        this.cdUsuario = cdUsuario;
        this.tipousuario = tipousuario;
        this.nickUsuario = nickUsuario;
        this.snUsuario = snUsuario;
        this.dsEmail = dsEmail;
        this.stAtivo = stAtivo;
        this.dtInclusao = dtInclusao;
    }
    public Usuario(int cdUsuario, Tipousuario tipousuario, String nickUsuario, String snUsuario, String dsEmail, Date dtUltimologin, boolean stAtivo, Date dtInclusao, Set<Apontamento> apontamentos, Set<Contratousuario> contratousuarios) {
       this.cdUsuario = cdUsuario;
       this.tipousuario = tipousuario;
       this.nickUsuario = nickUsuario;
       this.snUsuario = snUsuario;
       this.dsEmail = dsEmail;
       this.dtUltimologin = dtUltimologin;
       this.stAtivo = stAtivo;
       this.dtInclusao = dtInclusao;
       this.apontamentos = apontamentos;
       this.contratousuarios = contratousuarios;
    }
   
     @Id 

    
    @Column(name="cd_usuario", nullable=false)
    public int getCdUsuario() {
        return this.cdUsuario;
    }
    
    public void setCdUsuario(int cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cd_tipousuario", nullable=false)
    public Tipousuario getTipousuario() {
        return this.tipousuario;
    }
    
    public void setTipousuario(Tipousuario tipousuario) {
        this.tipousuario = tipousuario;
    }

    
    @Column(name="nick_usuario", nullable=false, length=40)
    public String getNickUsuario() {
        return this.nickUsuario;
    }
    
    public void setNickUsuario(String nickUsuario) {
        this.nickUsuario = nickUsuario;
    }

    
    @Column(name="sn_usuario", nullable=false, length=80)
    public String getSnUsuario() {
        return this.snUsuario;
    }
    
    public void setSnUsuario(String snUsuario) {
        this.snUsuario = snUsuario;
    }

    
    @Column(name="ds_email", nullable=false, length=100)
    public String getDsEmail() {
        return this.dsEmail;
    }
    
    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dt_ultimologin", length=29)
    public Date getDtUltimologin() {
        return this.dtUltimologin;
    }
    
    public void setDtUltimologin(Date dtUltimologin) {
        this.dtUltimologin = dtUltimologin;
    }

    
    @Column(name="st_ativo", nullable=false)
    public boolean isStAtivo() {
        return this.stAtivo;
    }
    
    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="dt_inclusao", nullable=false, length=29)
    public Date getDtInclusao() {
        return this.dtInclusao;
    }
    
    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<Apontamento> getApontamentos() {
        return this.apontamentos;
    }
    
    public void setApontamentos(Set<Apontamento> apontamentos) {
        this.apontamentos = apontamentos;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="usuario")
    public Set<Contratousuario> getContratousuarios() {
        return this.contratousuarios;
    }
    
    public void setContratousuarios(Set<Contratousuario> contratousuarios) {
        this.contratousuarios = contratousuarios;
    }




}


