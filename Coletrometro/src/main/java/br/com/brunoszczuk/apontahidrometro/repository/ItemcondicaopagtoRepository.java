/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Itemcondicaopagto;
import br.com.brunoszczuk.apontahidrometro.entities.ItemcondicaopagtoId;
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
public interface ItemcondicaopagtoRepository extends JpaRepository<Itemcondicaopagto, ItemcondicaopagtoId>{
    @Modifying
    @Query("delete from Itemcondicaopagto i where i.id.cdCondicaopagto = ?1")
    void deleteItemcondicaopagtoByCdCondicaoPagto(String cdCondicaopagto);
}
