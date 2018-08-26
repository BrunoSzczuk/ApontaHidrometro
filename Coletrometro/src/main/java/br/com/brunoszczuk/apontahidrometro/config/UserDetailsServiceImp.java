/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.config;

import br.com.brunoszczuk.apontahidrometro.domain.Status;
import br.com.brunoszczuk.apontahidrometro.domain.TipoUsuario;
import br.com.brunoszczuk.apontahidrometro.domain.Usuario;
import br.com.brunoszczuk.apontahidrometro.repository.TipoUsuarioRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bruno.szczuk
 */
@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepository userDetailsDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = userDetailsDao.findById(username).get();
        UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(user.getStAtivo() == Status.INATIVO);
            builder.password(user.getSnUsuario());
            String authorities = user.getTipoUsuario().getDsTipo();
            builder.roles("Equipamento");
            builder.authorities(authorities);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
