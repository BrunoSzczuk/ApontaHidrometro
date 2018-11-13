/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bruno
 */
@Transactional
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, String>{
    @Query(value = "Select * from contrato as c "
            + " inner join unidadeconsumidora as u on (c.cd_unidadeconsumidora = u.cd_unidadeconsumidora)"
            + " inner join cliente as cli on (c.cd_cliente = cli.cd_cliente) "
            + " where c.st_ativo"
            + "   and c.dt_periodofinal > current_date"
            + "   and cli.st_status ", name = "findByContratoAtivo", nativeQuery = true)
    List<Contrato> findByContratoAtivo();
            
            
}
