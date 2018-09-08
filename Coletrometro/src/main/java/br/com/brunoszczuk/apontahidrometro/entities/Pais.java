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
 * Pais generated by hbm2java
 */
@Entity
@Table(name="pais"
    ,schema="public"
)
public class Pais  implements java.io.Serializable {


     private String cdPais;
     private String dsPais;
     private String dsRegiao;
     private String dsSubregiao;
     private String cdCidadecapital;
     private String sgPais;
     private String urlBandeira;
     private boolean stAtivo;
     private Set<Estado> estados = new HashSet<Estado>(0);

    public Pais() {
    }

	
    public Pais(String cdPais, String dsPais, String sgPais, boolean stAtivo) {
        this.cdPais = cdPais;
        this.dsPais = dsPais;
        this.sgPais = sgPais;
        this.stAtivo = stAtivo;
    }
    public Pais(String cdPais, String dsPais, String dsRegiao, String dsSubregiao, String cdCidadecapital, String sgPais, String urlBandeira, boolean stAtivo, Set<Estado> estados) {
       this.cdPais = cdPais;
       this.dsPais = dsPais;
       this.dsRegiao = dsRegiao;
       this.dsSubregiao = dsSubregiao;
       this.cdCidadecapital = cdCidadecapital;
       this.sgPais = sgPais;
       this.urlBandeira = urlBandeira;
       this.stAtivo = stAtivo;
       this.estados = estados;
    }
   
     @Id 

    
    @Column(name="cd_pais", nullable=false, length=20)
    public String getCdPais() {
        return this.cdPais;
    }
    
    public void setCdPais(String cdPais) {
        this.cdPais = cdPais;
    }

    
    @Column(name="ds_pais", nullable=false, length=100)
    public String getDsPais() {
        return this.dsPais;
    }
    
    public void setDsPais(String dsPais) {
        this.dsPais = dsPais;
    }

    
    @Column(name="ds_regiao", length=100)
    public String getDsRegiao() {
        return this.dsRegiao;
    }
    
    public void setDsRegiao(String dsRegiao) {
        this.dsRegiao = dsRegiao;
    }

    
    @Column(name="ds_subregiao", length=100)
    public String getDsSubregiao() {
        return this.dsSubregiao;
    }
    
    public void setDsSubregiao(String dsSubregiao) {
        this.dsSubregiao = dsSubregiao;
    }

    
    @Column(name="cd_cidadecapital", length=20)
    public String getCdCidadecapital() {
        return this.cdCidadecapital;
    }
    
    public void setCdCidadecapital(String cdCidadecapital) {
        this.cdCidadecapital = cdCidadecapital;
    }

    
    @Column(name="sg_pais", nullable=false, length=5)
    public String getSgPais() {
        return this.sgPais;
    }
    
    public void setSgPais(String sgPais) {
        this.sgPais = sgPais;
    }

    
    @Column(name="url_bandeira")
    public String getUrlBandeira() {
        return this.urlBandeira;
    }
    
    public void setUrlBandeira(String urlBandeira) {
        this.urlBandeira = urlBandeira;
    }

    
    @Column(name="st_ativo", nullable=false)
    public boolean isStAtivo() {
        return this.stAtivo;
    }
    
    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="pais")
    public Set<Estado> getEstados() {
        return this.estados;
    }
    
    public void setEstados(Set<Estado> estados) {
        this.estados = estados;
    }




}

