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
 * Fotoapontamento generated by hbm2java
 */
@Entity
@Table(name="fotoapontamento"
    ,schema="public"
)
public class Fotoapontamento  implements java.io.Serializable {


     private int cdFoto;
     private byte[] ftApontamento;
     private Set<Apontamento> apontamentos = new HashSet<>(0);

    public Fotoapontamento() {
    }

	
    public Fotoapontamento(int cdFoto, byte[] ftApontamento) {
        this.cdFoto = cdFoto;
        this.ftApontamento = ftApontamento;
    }
    public Fotoapontamento(int cdFoto, byte[] ftApontamento, Set<Apontamento> apontamentos) {
       this.cdFoto = cdFoto;
       this.ftApontamento = ftApontamento;
       this.apontamentos = apontamentos;
    }
   
     @Id 

    
    @Column(name="cd_foto", nullable=false)
    public int getCdFoto() {
        return this.cdFoto;
    }
    
    public void setCdFoto(int cdFoto) {
        this.cdFoto = cdFoto;
    }

    
    @Column(name="ft_apontamento", nullable=false)
    public byte[] getFtApontamento() {
        return this.ftApontamento;
    }
    
    public void setFtApontamento(byte[] ftApontamento) {
        this.ftApontamento = ftApontamento;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="fotoapontamento")
    public Set<Apontamento> getApontamentos() {
        return this.apontamentos;
    }
    
    public void setApontamentos(Set<Apontamento> apontamentos) {
        this.apontamentos = apontamentos;
    }




}


