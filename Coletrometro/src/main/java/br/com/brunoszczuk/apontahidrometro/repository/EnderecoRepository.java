/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.repository;

import br.com.brunoszczuk.apontahidrometro.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bruno
 */
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
    
}
