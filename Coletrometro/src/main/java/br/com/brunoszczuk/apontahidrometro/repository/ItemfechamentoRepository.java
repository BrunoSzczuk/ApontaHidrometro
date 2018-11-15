/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Fechamentoapontamento;
import br.com.brunoszczuk.apontahidrometro.entities.Itemfechamento;
import br.com.brunoszczuk.apontahidrometro.entities.ItemfechamentoId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bruno
 */
@Transactional
@Repository
public interface ItemfechamentoRepository extends JpaRepository<Itemfechamento, ItemfechamentoId>{
    List<Itemfechamento> findByfechamentoapontamento(Fechamentoapontamento fechamentoapontamento);
}
