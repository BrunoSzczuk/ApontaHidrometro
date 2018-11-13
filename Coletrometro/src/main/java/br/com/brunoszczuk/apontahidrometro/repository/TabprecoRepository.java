/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Tabpreco;
import java.time.LocalDate;
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
public interface TabprecoRepository extends JpaRepository<Tabpreco, Integer> {

    @Query(value = "select * from tabpreco t "
            + "  where st_ativo"
            + "    and dt_vigenciaini <= current_date"
            + "    and dt_vigenciafim >= current_date", nativeQuery = true)
    Tabpreco findByTabelaVigente(LocalDate dtini);
}
