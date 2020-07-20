package com.player.es;

import com.player.es.Utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);

    }

}
