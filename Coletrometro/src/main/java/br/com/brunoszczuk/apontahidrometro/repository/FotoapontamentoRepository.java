/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Apontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Fotoapontamento;
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
public interface FotoapontamentoRepository extends JpaRepository<Fotoapontamento, Integer> {

    @Query(value = "select * from fotoapontamento as f\n"
            + " inner join apontamento as a on (a.cd_foto = f.cd_foto)\n" +
            " where a.cd_apontamento = ?1", nativeQuery = true)
    Fotoapontamento findByApontamento(int a);
}
