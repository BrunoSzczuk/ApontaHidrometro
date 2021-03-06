package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 14/11/2018 12:26:39 by Hibernate Tools 4.3.1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Itemfechamento generated by hbm2java
 */
@Entity
@Table(name = "itemfechamento",
        schema = "public"
)
public class Itemfechamento implements java.io.Serializable {

    private ItemfechamentoId id;
    private Apontamento apontamento;
    private Fechamentoapontamento fechamentoapontamento;

    public Itemfechamento() {
    }

    public Itemfechamento(ItemfechamentoId id, Apontamento apontamento, Fechamentoapontamento fechamentoapontamento) {
        this.id = id;
        this.apontamento = apontamento;
        this.fechamentoapontamento = fechamentoapontamento;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "cdFechamento", column = @Column(name = "cd_fechamento", nullable = false))
        , 
        @AttributeOverride(name = "cdApontamento", column = @Column(name = "cd_apontamento", nullable = false))})
    public ItemfechamentoId getId() {
        return this.id;
    }

    public void setId(ItemfechamentoId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_apontamento", nullable = false, insertable = false, updatable = false)
    public Apontamento getApontamento() {
        return this.apontamento;
    }

    public void setApontamento(Apontamento apontamento) {
        this.apontamento = apontamento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_fechamento", nullable = false, insertable = false, updatable = false)
    public Fechamentoapontamento getFechamentoapontamento() {
        return this.fechamentoapontamento;
    }

    public void setFechamentoapontamento(Fechamentoapontamento fechamentoapontamento) {
        this.fechamentoapontamento = fechamentoapontamento;
    }

}
