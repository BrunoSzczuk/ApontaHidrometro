package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 29/08/2018 23:02:23 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Condicaopagto generated by hbm2java
 */
@Entity
@Table(name="condicaopagto"
    ,schema="public"
)
public class Condicaopagto  implements java.io.Serializable {


     private String cdCondicaopagto;
     private String dsCondicaopagto;
     private boolean stAtivo;
     private Set<Itemcondicaopagto> itemcondicaopagtos = new HashSet<Itemcondicaopagto>(0);
     private Set<Contrato> contratos = new HashSet<Contrato>(0);

    public Condicaopagto() {
    }

	
    public Condicaopagto(String cdCondicaopagto, String dsCondicaopagto, boolean stAtivo) {
        this.cdCondicaopagto = cdCondicaopagto;
        this.dsCondicaopagto = dsCondicaopagto;
        this.stAtivo = stAtivo;
    }
    public Condicaopagto(String cdCondicaopagto, String dsCondicaopagto, boolean stAtivo, Set<Itemcondicaopagto> itemcondicaopagtos, Set<Contrato> contratos) {
       this.cdCondicaopagto = cdCondicaopagto;
       this.dsCondicaopagto = dsCondicaopagto;
       this.stAtivo = stAtivo;
       this.itemcondicaopagtos = itemcondicaopagtos;
       this.contratos = contratos;
    }
   
     @Id 

    
    @Column(name="cd_condicaopagto", nullable=false, length=3)
    public String getCdCondicaopagto() {
        return this.cdCondicaopagto;
    }
    
    public void setCdCondicaopagto(String cdCondicaopagto) {
        this.cdCondicaopagto = cdCondicaopagto;
    }

    
    @Column(name="ds_condicaopagto", nullable=false, length=100)
    public String getDsCondicaopagto() {
        return this.dsCondicaopagto;
    }
    
    public void setDsCondicaopagto(String dsCondicaopagto) {
        this.dsCondicaopagto = dsCondicaopagto;
    }

    
    @Column(name="st_ativo", nullable=false)
    public boolean isStAtivo() {
        return this.stAtivo;
    }
    
    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="condicaopagto")
    public Set<Itemcondicaopagto> getItemcondicaopagtos() {
        return this.itemcondicaopagtos;
    }
    
    public void setItemcondicaopagtos(Set<Itemcondicaopagto> itemcondicaopagtos) {
        this.itemcondicaopagtos = itemcondicaopagtos;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="condicaopagto")
    public Set<Contrato> getContratos() {
        return this.contratos;
    }
    
    public void setContratos(Set<Contrato> contratos) {
        this.contratos = contratos;
    }




}


