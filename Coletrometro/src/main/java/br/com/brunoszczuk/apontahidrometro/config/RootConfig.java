package br.com.brunoszczuk.apontahidrometro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Ballem on 07/06/2017.
 */
@Configuration
@ComponentScan("br.com.brunoszczuk.apontahidrometro")
@EnableWebMvc
public class RootConfig {
}
