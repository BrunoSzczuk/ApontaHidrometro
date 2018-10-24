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
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Contrato generated by hbm2java
 */
@Entity
@Table(name = "contrato",
        schema = "public"
)
public class Contrato implements java.io.Serializable {

    private String nrContrato;

    @NotNull(message = "{message.contrato.cliente}")
    private Cliente cliente;
    @NotNull(message = "{message.contrato.condicaopagto}")
    private Condicaopagto condicaopagto;
    @NotNull(message = "{message.contrato.frequenciacoleta}")
    private Frequenciacoleta frequenciacoleta;
    @NotNull(message = "{message.contrato.unidadeconsumidora}")
    private Unidadeconsumidora unidadeconsumidora;
    @NotBlank
    @Size(max = 100)
    private String dsContrato;
    @FutureOrPresent
    private Date dtPeriodoinicial;
    @Future
    private Date dtPeriodofinal;
    private boolean stAtivo;
    private Set<Apontamento> apontamentos = new HashSet<>(0);
    private Set<Contratousuario> contratousuarios = new HashSet<>(0);
    private Set<Fechamentoapontamento> fechamentoapontamentos = new HashSet<>(0);

    public Contrato() {
    }

    public Contrato(String nrContrato, Cliente cliente, Condicaopagto condicaopagto, Frequenciacoleta frequenciacoleta, Unidadeconsumidora unidadeconsumidora, String dsContrato, Date dtPeriodoinicial, boolean stAtivo) {
        this.nrContrato = nrContrato;
        this.cliente = cliente;
        this.condicaopagto = condicaopagto;
        this.frequenciacoleta = frequenciacoleta;
        this.unidadeconsumidora = unidadeconsumidora;
        this.dsContrato = dsContrato;
        this.dtPeriodoinicial = dtPeriodoinicial;
        this.stAtivo = stAtivo;
    }

    public Contrato(String nrContrato, Cliente cliente, Condicaopagto condicaopagto, Frequenciacoleta frequenciacoleta, Unidadeconsumidora unidadeconsumidora, String dsContrato, Date dtPeriodoinicial, Date dtPeriodofinal, boolean stAtivo, Set<Apontamento> apontamentos, Set<Contratousuario> contratousuarios, Set<Fechamentoapontamento> fechamentoapontamentos) {
        this.nrContrato = nrContrato;
        this.cliente = cliente;
        this.condicaopagto = condicaopagto;
        this.frequenciacoleta = frequenciacoleta;
        this.unidadeconsumidora = unidadeconsumidora;
        this.dsContrato = dsContrato;
        this.dtPeriodoinicial = dtPeriodoinicial;
        this.dtPeriodofinal = dtPeriodofinal;
        this.stAtivo = stAtivo;
        this.apontamentos = apontamentos;
        this.contratousuarios = contratousuarios;
        this.fechamentoapontamentos = fechamentoapontamentos;
    }

    @Id

    @Column(name = "nr_contrato", nullable = false, length = 9)
    public String getNrContrato() {
        return this.nrContrato;
    }

    public void setNrContrato(String nrContrato) {
        this.nrContrato = nrContrato;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_cliente", nullable = false)
    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_condicaopagto", nullable = false)
    public Condicaopagto getCondicaopagto() {
        return this.condicaopagto;
    }

    public void setCondicaopagto(Condicaopagto condicaopagto) {
        this.condicaopagto = condicaopagto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_frequenciacoleta", nullable = false)
    public Frequenciacoleta getFrequenciacoleta() {
        return this.frequenciacoleta;
    }

    public void setFrequenciacoleta(Frequenciacoleta frequenciacoleta) {
        this.frequenciacoleta = frequenciacoleta;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "cd_unidadeconsumidora", nullable = false)
    public Unidadeconsumidora getUnidadeconsumidora() {
        return this.unidadeconsumidora;
    }

    public void setUnidadeconsumidora(Unidadeconsumidora unidadeconsumidora) {
        this.unidadeconsumidora = unidadeconsumidora;
    }

    @Column(name = "ds_contrato", nullable = false, length = 100)
    public String getDsContrato() {
        return this.dsContrato;
    }

    public void setDsContrato(String dsContrato) {
        this.dsContrato = dsContrato;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_periodoinicial", nullable = false, length = 13)
    public Date getDtPeriodoinicial() {
        return this.dtPeriodoinicial;
    }

    public void setDtPeriodoinicial(Date dtPeriodoinicial) {
        this.dtPeriodoinicial = dtPeriodoinicial;
    }

    @Column(name = "dt_periodofinal")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDtPeriodofinal() {
        return this.dtPeriodofinal;
    }

    public void setDtPeriodofinal(Date dtPeriodofinal) {
        this.dtPeriodofinal = dtPeriodofinal;
    }

    @Column(name = "st_ativo", nullable = false)
    public boolean isStAtivo() {
        return this.stAtivo;
    }

    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato")
    public Set<Apontamento> getApontamentos() {
        return this.apontamentos;
    }

    public void setApontamentos(Set<Apontamento> apontamentos) {
        this.apontamentos = apontamentos;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato")
    public Set<Contratousuario> getContratousuarios() {
        return this.contratousuarios;
    }

    public void setContratousuarios(Set<Contratousuario> contratousuarios) {
        this.contratousuarios = contratousuarios;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato")
    public Set<Fechamentoapontamento> getFechamentoapontamentos() {
        return this.fechamentoapontamentos;
    }

    public void setFechamentoapontamentos(Set<Fechamentoapontamento> fechamentoapontamentos) {
        this.fechamentoapontamentos = fechamentoapontamentos;
    }

}
