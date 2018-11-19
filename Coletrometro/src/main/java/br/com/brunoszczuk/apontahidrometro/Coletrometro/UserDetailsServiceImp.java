/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.Coletrometro;

import br.com.brunoszczuk.apontahidrometro.entities.Permissao;
import br.com.brunoszczuk.apontahidrometro.entities.Permissaotipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Tipousuario;
import br.com.brunoszczuk.apontahidrometro.entities.Usuario;
import br.com.brunoszczuk.apontahidrometro.repository.PermissaotipousuarioRepository;
import br.com.brunoszczuk.apontahidrometro.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private PermissaotipousuarioRepository permissaotipousuarioRepository;

    @Transactional()
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = userDetailsDao.findByIgnoreCaseNickUsuarioOrIgnoreCaseDsEmail(username, username);
        UserBuilder builder = null;
        if (user != null) {
            user.setDtUltimologin(new Date());
            userDetailsDao.save(user);
            builder = User.withUsername(username);
            builder.disabled(!user.isStAtivo());
            builder.password(user.getSnUsuario());
            builder.authorities(authorities(user.getTipousuario()));
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return builder.build();
    }

    public Collection<? extends GrantedAuthority> authorities(Tipousuario tipousuario) {
        Collection<GrantedAuthority> auths = new ArrayList<>();

        List<Permissaotipousuario> lista = permissaotipousuarioRepository.findByTipoUsuario(tipousuario);
        for (Permissaotipousuario permissao : lista) {
            auths.add(new SimpleGrantedAuthority("ROLE_" + permissao.getPermissao().getNmRotina()));
        }

        return auths;
    }

    public Collection<? extends GrantedAuthority> authorities(Usuario usuario) {
        return authorities(usuario.getTipousuario());
    }
}
