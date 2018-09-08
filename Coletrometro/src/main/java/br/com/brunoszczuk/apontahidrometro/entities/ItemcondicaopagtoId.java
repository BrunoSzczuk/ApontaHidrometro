package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 29/08/2018 23:02:23 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ItemcondicaopagtoId generated by hbm2java
 */
@Embeddable
public class ItemcondicaopagtoId  implements java.io.Serializable {


     private String cdCondicaopagto;
     private int nrSequencia;

    public ItemcondicaopagtoId() {
    }

    public ItemcondicaopagtoId(String cdCondicaopagto, int nrSequencia) {
       this.cdCondicaopagto = cdCondicaopagto;
       this.nrSequencia = nrSequencia;
    }
   


    @Column(name="cd_condicaopagto", nullable=false, length=3)
    public String getCdCondicaopagto() {
        return this.cdCondicaopagto;
    }
    
    public void setCdCondicaopagto(String cdCondicaopagto) {
        this.cdCondicaopagto = cdCondicaopagto;
    }


    @Column(name="nr_sequencia", nullable=false)
    public int getNrSequencia() {
        return this.nrSequencia;
    }
    
    public void setNrSequencia(int nrSequencia) {
        this.nrSequencia = nrSequencia;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ItemcondicaopagtoId) ) return false;
		 ItemcondicaopagtoId castOther = ( ItemcondicaopagtoId ) other; 
         
		 return ( (this.getCdCondicaopagto()==castOther.getCdCondicaopagto()) || ( this.getCdCondicaopagto()!=null && castOther.getCdCondicaopagto()!=null && this.getCdCondicaopagto().equals(castOther.getCdCondicaopagto()) ) )
 && (this.getNrSequencia()==castOther.getNrSequencia());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCdCondicaopagto() == null ? 0 : this.getCdCondicaopagto().hashCode() );
         result = 37 * result + this.getNrSequencia();
         return result;
   }   


}

