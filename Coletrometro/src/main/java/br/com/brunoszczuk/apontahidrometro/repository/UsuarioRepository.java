/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bruno.szczuk
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Tipousuario findByTipousuario(String id);
    Usuario findByIgnoreCaseNickUsuario(String nickUsuario);    
    Usuario findByIgnoreCaseNickUsuarioOrIgnoreCaseDsEmail(String nickUsuario, String dsEmail);
}
