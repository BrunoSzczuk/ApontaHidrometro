package br.com.brunoszczuk.apontahidrometro.entities;
// Generated 29/08/2018 23:02:23 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Fechamentoapontamento generated by hbm2java
 */
@Entity
@Table(name = "fechamentoapontamento",
        schema = "public"
)
public class Fechamentoapontamento implements java.io.Serializable {

    private int cdFechamento;
    @NotNull
    private Competencia competencia;
    @NotNull
    private Contrato contrato;
    @NotNull
    private Tabpreco tabpreco;
    @Min((long) 0.1)
    private float vlFechamento;
    private Date dtInclusao;
    private Set<Itemfechamento> itemfechamentos = new HashSet<>(0);

    public Fechamentoapontamento() {
    }

    public Fechamentoapontamento(int cdFechamento, Competencia competencia, Contrato contrato, Tabpreco tabpreco, float vlFechamento, Date dtInclusao) {
        this.cdFechamento = cdFechamento;
        this.competencia = competencia;
        this.contrato = contrato;
        this.tabpreco = tabpreco;
        this.vlFechamento = vlFechamento;
        this.dtInclusao = dtInclusao;
    }

    public Fechamentoapontamento(int cdFechamento, Competencia competencia, Contrato contrato, Tabpreco tabpreco, float vlFechamento, Date dtInclusao, Set<Itemfechamento> itemfechamentos) {
        this.cdFechamento = cdFechamento;
        this.competencia = competencia;
        this.contrato = contrato;
        this.tabpreco = tabpreco;

        this.vlFechamento = vlFechamento;
        this.dtInclusao = dtInclusao;
        this.itemfechamentos = itemfechamentos;
    }

    @Id

    @Column(name = "cd_fechamento", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getCdFechamento() {
        return this.cdFechamento;
    }

    public void setCdFechamento(int cdFechamento) {
        this.cdFechamento = cdFechamento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_competencia", nullable = false)
    public Competencia getCompetencia() {
        return this.competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "nr_contrato", nullable = false)
    public Contrato getContrato() {
        return this.contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_tabpreco", nullable = false)
    public Tabpreco getTabpreco() {
        return this.tabpreco;
    }

    public void setTabpreco(Tabpreco tabpreco) {
        this.tabpreco = tabpreco;
    }

    @Column(name = "vl_fechamento", nullable = false, precision = 8, scale = 8)
    public float getVlFechamento() {
        return this.vlFechamento;
    }

    public void setVlFechamento(float vlFechamento) {
        this.vlFechamento = vlFechamento;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_inclusao", nullable = false, length = 29)
    public Date getDtInclusao() {
        return this.dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fechamentoapontamento")
    @Fetch(FetchMode.JOIN)
    public Set<Itemfechamento> getItemfechamentos() {
        return this.itemfechamentos;
    }

    public void setItemfechamentos(Set<Itemfechamento> itemfechamentos) {
        this.itemfechamentos = itemfechamentos;
    }

}
