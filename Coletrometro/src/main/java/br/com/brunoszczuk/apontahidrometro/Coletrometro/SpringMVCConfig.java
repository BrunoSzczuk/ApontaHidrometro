/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.brunoszczuk.apontahidrometro.Coletrometro;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

/**
 *
 * @author bruno.szczuk
 */
@Configuration
@ComponentScan("br.com.brunoszczuk.apontahidrometro")
public class SpringMVCConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").
                addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(true);
    }

    //Adição do Dialeto do springsecurity para o Thymeleaf
    @Bean
    @ConditionalOnMissingBean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    //Utilização do Multipart para as imagens
   /* @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10 * 1024 * 1024); //10MB de limite de arquivo
        return multipartResolver;
    }*/
}
