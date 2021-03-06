/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Equipamento;
import br.com.brunoszczuk.apontahidrometro.entities.Unidadeconsumidora;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bruno
 */
@Transactional
@Repository
public interface ApontamentoRepository extends JpaRepository<Apontamento, Integer> {

    @Query(value = "select * from apontamento  as a\n"
            + " inner join contrato as c on (c.nr_contrato = a.nr_contrato)\n"
            + " inner join unidadeconsumidora as u on (c.cd_unidadeconsumidora = u.cd_unidadeconsumidora)\n"
            + " where a.dt_inclusao between ?1 and ?2\n"
            + "   and u.cd_unidadeconsumidora = ?3", nativeQuery = true)
    List<Apontamento> findBydtInclusaoBetweenAndUnidadeconsumidora(Date dtInicio, Date dtFim, Unidadeconsumidora unidadeconsumidora);

    List<Apontamento> findBydtInclusaoBetween(Date dtInicio, Date dtFim);

    List<Apontamento> findByEquipamento(Equipamento equipamento);

    @Query(value = "select * from apontamento as a\n"
            + " left join itemfechamento as i on (i.cd_apontamento = a.cd_apontamento)\n"
            + " inner join equipamento as e on (e.cd_equipamento = a.cd_equipamento)\n"
            + " inner join unidadeconsumidora as un on (un.cd_equipamento = a.cd_equipamento)\n"
            + " inner join contrato as c on (c.cd_unidadeconsumidora = un.cd_unidadeconsumidora)\n"
            + " inner join cliente as cli on (un.cd_cliente = cli.cd_cliente)\n"
            + " where e.st_ativo\n"
            + "   and i.cd_apontamento is null\n"
            + "   and c.st_ativo\n"
            + "   and cli.st_status\n"
            + "   and c.dt_periodoinicial <= current_date\n"
            + "   and c.dt_periodofinal >= current_date"
            + "   and not a.st_fechado"
            + "   and a.cd_equipamento = ?1 "
            + "   and a.dt_inclusao between ?2 and ?3 "
            + " order by a.dt_inclusao asc", nativeQuery = true)
    List<Apontamento> findByEquipamentoandContratoAtivo(Equipamento equipamento, LocalDate dtIni, LocalDate dtFim);

    @Modifying
    @Query(value = "update apontamento as i  "
            + "set st_fechado = ?2 "
            + "where i.cd_apontamento in ( select a.cd_apontamento from equipamento as e\n"
            + " inner join unidadeconsumidora as u on (e.cd_equipamento = u.cd_equipamento)\n"
            + " inner join contrato as c on (u.cd_unidadeconsumidora = c.cd_unidadeconsumidora)\n"
            + " inner join apontamento as a on (a.cd_equipamento = e.cd_equipamento)\n"
            + " inner join itemfechamento as f on (f.cd_apontamento = a.cd_apontamento)\n"
            + "  where f.cd_fechamento = ?1 )", nativeQuery = true)
    void updateApontamentoSetStatus(int cdApontamento, boolean status);

    @Query(value = "select cast(coalesce(max(a.cont_apontado),e.cont_inicial,0)as integer) from apontamento as a\n"
            + " inner join equipamento as e on (e.cd_equipamento = a.cd_equipamento)\n"
            + " where a.cd_equipamento = ?1\n"
            + " group by e.cont_inicial", nativeQuery = true)
    int findUltimoContador(int cdEquipamento);

    @Query(value = "select exists(select 1 from apontamento as a\n"
            + " where dt_inclusao < ?2\n"
            + "   and not a.st_fechado"
            + "   and a.cd_equipamento = ?1)", nativeQuery = true)
    boolean hasApontamentosAnteriores(int cdEquipamento,LocalDate dataInicio);
}
