/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Permissaotipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bruno.szczuk
 */
@Repository
public interface PermissaotipousuarioRepository extends JpaRepository<Permissaotipousuario, Integer>{
    @Query("select i from Permissaotipousuario i where i.tipousuario = :tipousuario")
    List<Permissaotipousuario> findByTipoUsuario(@Param("tipousuario") Tipousuario Tipousuario);
}
