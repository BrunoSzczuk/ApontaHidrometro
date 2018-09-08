package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 29/08/2018 23:02:23 by Hibernate Tools 4.3.1


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

/**
 * Estado generated by hbm2java
 */
@Entity
@Table(name="estado"
    ,schema="public"
)
public class Estado  implements java.io.Serializable {


     private String cdEstado;
     private Pais pais;
     private String sgEstado;
     private String dsEstado;
     private boolean stAtivo;
     private Set<Municipio> municipios = new HashSet<Municipio>(0);

    public Estado() {
    }

	
    public Estado(String cdEstado, Pais pais, String sgEstado, String dsEstado, boolean stAtivo) {
        this.cdEstado = cdEstado;
        this.pais = pais;
        this.sgEstado = sgEstado;
        this.dsEstado = dsEstado;
        this.stAtivo = stAtivo;
    }
    public Estado(String cdEstado, Pais pais, String sgEstado, String dsEstado, boolean stAtivo, Set<Municipio> municipios) {
       this.cdEstado = cdEstado;
       this.pais = pais;
       this.sgEstado = sgEstado;
       this.dsEstado = dsEstado;
       this.stAtivo = stAtivo;
       this.municipios = municipios;
    }
   
     @Id 

    
    @Column(name="cd_estado", nullable=false, length=20)
    public String getCdEstado() {
        return this.cdEstado;
    }
    
    public void setCdEstado(String cdEstado) {
        this.cdEstado = cdEstado;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cd_pais", nullable=false)
    public Pais getPais() {
        return this.pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    
    @Column(name="sg_estado", nullable=false, length=5)
    public String getSgEstado() {
        return this.sgEstado;
    }
    
    public void setSgEstado(String sgEstado) {
        this.sgEstado = sgEstado;
    }

    
    @Column(name="ds_estado", nullable=false, length=100)
    public String getDsEstado() {
        return this.dsEstado;
    }
    
    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }

    
    @Column(name="st_ativo", nullable=false)
    public boolean isStAtivo() {
        return this.stAtivo;
    }
    
    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="estado")
    public Set<Municipio> getMunicipios() {
        return this.municipios;
    }
    
    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
    }




}

