/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author bruno.szczuk
 */
@Entity
@Table(name = "equipamento")
public class Equipamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_equipamento")
    private long cdEquipamento;

    @Column(name = "nr_serie", length = 60)
    @NotBlank(message = "É necessário informar um Número de Série")
    private String nrSerie;

    @Column(name = "cont_inicial")
    @NotNull(message = "É necessário informar um valor de Contador Inicial")
    private long contInicial;

    @Column(name = "cont_atual")
    @NotNull(message = "É necessário informar um valor de Contador Atual")
    private long contAtual;

    @Column(name = "ds_equipamento", length = 100)
    @NotBlank(message = "É necessário informar uma Descrição desse Equipamento")
    private String dsEquipamento;

    @Column(name = "st_ativo", length = 20)
    @Enumerated(EnumType.STRING)
    private Status stAtivo;

    @Override
    public String toString() {
        return "Equipamento{" + "cdEquipamento=" + cdEquipamento + ", nrSerie=" + nrSerie + ", contInicial=" + contInicial + ", contAtual=" + contAtual + ", dsEquipamento=" + dsEquipamento + ", stAtivo=" + stAtivo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.cdEquipamento ^ (this.cdEquipamento >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipamento other = (Equipamento) obj;
        if (this.cdEquipamento != other.cdEquipamento) {
            return false;
        }
        return true;
    }

    public long getCdEquipamento() {
        return cdEquipamento;
    }

    public void setCdEquipamento(long cdEquipamento) {
        this.cdEquipamento = cdEquipamento;
    }

    public String getNrSerie() {
        return nrSerie;
    }

    public void setNrSerie(String nrSerie) {
        this.nrSerie = nrSerie;
    }

    public long getContInicial() {
        return contInicial;
    }

    public void setContInicial(long contInicial) {
        this.contInicial = contInicial;
    }

    public long getContAtual() {
        return contAtual;
    }

    public void setContAtual(long contAtual) {
        this.contAtual = contAtual;
    }

    public String getDsEquipamento() {
        return dsEquipamento;
    }

    public void setDsEquipamento(String dsEquipamento) {
        this.dsEquipamento = dsEquipamento;
    }

    public Status getStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(Status stAtivo) {
        this.stAtivo = stAtivo;
    }

    public Equipamento() {
    }
}
