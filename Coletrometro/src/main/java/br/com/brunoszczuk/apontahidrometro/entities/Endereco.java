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
 * Endereco generated by hbm2java
 */
@Entity
@Table(name="endereco"
    ,schema="public"
)
public class Endereco  implements java.io.Serializable {


     private int cdEndereco;
     private Municipio municipio;
     private String nrLogradouro;
     private String nmBairro;
     private String nrCep;
     private String nmRua;
     private String nrLatitude;
     private String nrLongitude;
     private Set<Unidadeconsumidora> unidadeconsumidoras = new HashSet<Unidadeconsumidora>(0);
     private Set<Cliente> clientes = new HashSet<Cliente>(0);

    public Endereco() {
    }

	
    public Endereco(int cdEndereco, Municipio municipio, String nmBairro, String nrCep, String nmRua) {
        this.cdEndereco = cdEndereco;
        this.municipio = municipio;
        this.nmBairro = nmBairro;
        this.nrCep = nrCep;
        this.nmRua = nmRua;
    }
    public Endereco(int cdEndereco, Municipio municipio, String nrLogradouro, String nmBairro, String nrCep, String nmRua, String nrLatitude, String nrLongitude, Set<Unidadeconsumidora> unidadeconsumidoras, Set<Cliente> clientes) {
       this.cdEndereco = cdEndereco;
       this.municipio = municipio;
       this.nrLogradouro = nrLogradouro;
       this.nmBairro = nmBairro;
       this.nrCep = nrCep;
       this.nmRua = nmRua;
       this.nrLatitude = nrLatitude;
       this.nrLongitude = nrLongitude;
       this.unidadeconsumidoras = unidadeconsumidoras;
       this.clientes = clientes;
    }
   
     @Id 

    
    @Column(name="cd_endereco", nullable=false)
    public int getCdEndereco() {
        return this.cdEndereco;
    }
    
    public void setCdEndereco(int cdEndereco) {
        this.cdEndereco = cdEndereco;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cd_municipio", nullable=false)
    public Municipio getMunicipio() {
        return this.municipio;
    }
    
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    
    @Column(name="nr_logradouro", length=10)
    public String getNrLogradouro() {
        return this.nrLogradouro;
    }
    
    public void setNrLogradouro(String nrLogradouro) {
        this.nrLogradouro = nrLogradouro;
    }

    
    @Column(name="nm_bairro", nullable=false, length=80)
    public String getNmBairro() {
        return this.nmBairro;
    }
    
    public void setNmBairro(String nmBairro) {
        this.nmBairro = nmBairro;
    }

    
    @Column(name="nr_cep", nullable=false, length=8)
    public String getNrCep() {
        return this.nrCep;
    }
    
    public void setNrCep(String nrCep) {
        this.nrCep = nrCep;
    }

    
    @Column(name="nm_rua", nullable=false, length=80)
    public String getNmRua() {
        return this.nmRua;
    }
    
    public void setNmRua(String nmRua) {
        this.nmRua = nmRua;
    }

    
    @Column(name="nr_latitude", length=100)
    public String getNrLatitude() {
        return this.nrLatitude;
    }
    
    public void setNrLatitude(String nrLatitude) {
        this.nrLatitude = nrLatitude;
    }

    
    @Column(name="nr_longitude", length=100)
    public String getNrLongitude() {
        return this.nrLongitude;
    }
    
    public void setNrLongitude(String nrLongitude) {
        this.nrLongitude = nrLongitude;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="endereco")
    public Set<Unidadeconsumidora> getUnidadeconsumidoras() {
        return this.unidadeconsumidoras;
    }
    
    public void setUnidadeconsumidoras(Set<Unidadeconsumidora> unidadeconsumidoras) {
        this.unidadeconsumidoras = unidadeconsumidoras;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="endereco")
    public Set<Cliente> getClientes() {
        return this.clientes;
    }
    
    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }




}

