package br.com.helton.projeto_ponto_eletronico.infra.externalApi;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalAppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
