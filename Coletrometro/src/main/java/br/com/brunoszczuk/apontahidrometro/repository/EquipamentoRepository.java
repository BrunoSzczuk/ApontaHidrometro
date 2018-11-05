/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bruno.szczuk
 */
@Repository
@Transactional
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    Equipamento findByStAtivoTrue();

    @Modifying
    @Query(value = "Select * from equipamento as e "
            + " inner join unidadeconsumidora as u on (e.cd_equipamento = u.cd_equipamento)"
            + " inner join contrato as c on (c.cd_unidadeconsumidora = u.cd_unidadeconsumidora)"
            + " inner join cliente as cli on (c.cd_cliente = cli.cd_cliente) "
            + " where e.st_ativo"
            + "   and c.st_ativo"
            + "   and c.dt_periodofinal > current_date"
            + "   and cli.st_status ", name = "findByContratoAtivo", nativeQuery = true)
    List<Equipamento> findByContratoAtivo();
}
