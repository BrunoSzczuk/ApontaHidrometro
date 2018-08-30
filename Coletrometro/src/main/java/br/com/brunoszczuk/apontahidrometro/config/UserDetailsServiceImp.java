/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.config;

import br.com.brunoszczuk.apontahidrometro.entities.Usuario;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import java.util.Calendar;
import java.util.Date;

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

    @Transactional()
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = userDetailsDao.findByIgnoreCaseNickUsuarioOrIgnoreCaseDsEmail(username, username);
        UserBuilder builder = null;
        if (user != null) {
            user.setDtUltimologin( new Date());
            userDetailsDao.save(user);
            builder = User.withUsername(username);
            builder.disabled(!user.isStAtivo());
            builder.password(user.getSnUsuario());
            String authorities = user.getTipousuario().getDsTipousuario();

            builder.authorities(authorities);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return builder.build();
    }
}
