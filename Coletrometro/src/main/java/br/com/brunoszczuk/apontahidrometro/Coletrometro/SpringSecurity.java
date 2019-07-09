/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.Coletrometro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author bruno.szczuk
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**", "/static/**", "/webjars/**", "/css/**", "/js/**").permitAll()
                .and().authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/apontamento/").hasRole("apontamento")
                .antMatchers("/cliente/").hasRole("cliente")
                .antMatchers("/competencia/").hasRole("competencia")
                .antMatchers("/condicaopagto/").hasRole("condicaopagto")
                .antMatchers("/contareceber/").hasRole("contareceber")
                .antMatchers("/contrato/").hasRole("contrato")
                .antMatchers("/contratousuario/").hasRole("contratousuario")
                .antMatchers("/endereco/").hasRole("endereco")
                .antMatchers("/equipamento/").hasRole("equipamento")
                .antMatchers("/estado/").hasRole("estado")
                .antMatchers("/frequenciacoleta/").hasRole("frequenciacoleta")
                .antMatchers("/fechamentoapontamento/").hasRole("fechamentoapontamento")
                .antMatchers("/formapagto/").hasRole("formapagto")
                .antMatchers("/municipio/").hasRole("municipio")
                .antMatchers("/pais/").hasRole("pais")
                .antMatchers("/permissao/").hasRole("permissao")
                .antMatchers("/tipousuario/").hasRole("tipousuario")
                .antMatchers("/tabpreco/").hasRole("tabpreco")
                .antMatchers("/unddconsumidora/").hasRole("unddconsumidora")
                .antMatchers("/usuario/").hasRole("usuario")
                
                //	.antMatchers("/vendas/relatorios/custos").hasRole("VISUALIZAR_RELATORIO_CUSTOS")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().rememberMe();
        /* http.authorizeRequests().anyRequest().hasAnyRole("Administrador", "Usuário")
                .and()
                .authorizeRequests().antMatchers("/login**").permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/loginAction").permitAll()
                .and()
                .logout().logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable();*/
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }
}
