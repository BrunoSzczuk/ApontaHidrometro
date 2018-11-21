/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.controller;

import br.com.brunoszczuk.apontahidrometro.entities.Unidadeconsumidora;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author bruno.szczuk
 */
public class Relatorioconsumo implements Serializable{
    @NotNull
    private Unidadeconsumidora unidadeconsumidora;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtPeriodoinicial;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtPeriodofinal;

    public Relatorioconsumo(Unidadeconsumidora unidadeconsumidora, Date dtPeriodoinicial, Date dtPeriodofinal) {
        this.unidadeconsumidora = unidadeconsumidora;
        this.dtPeriodoinicial = dtPeriodoinicial;
        this.dtPeriodofinal = dtPeriodofinal;
    }

    public Relatorioconsumo() {
    }

    public Unidadeconsumidora getUnidadeconsumidora() {
        return unidadeconsumidora;
    }

    public void setUnidadeconsumidora(Unidadeconsumidora unidadeconsumidora) {
        this.unidadeconsumidora = unidadeconsumidora;
    }

    public Date getdtPeriodoinicial() {
        return dtPeriodoinicial;
    }

    public void setdtPeriodoinicial(Date dtPeriodoinicial) {
        this.dtPeriodoinicial = dtPeriodoinicial;
    }

    public Date getdtPeriodofinal() {
        return dtPeriodofinal;
    }

    public void setdtPeriodofinal(Date dtPeriodofinal) {
        this.dtPeriodofinal = dtPeriodofinal;
    }
    
}
