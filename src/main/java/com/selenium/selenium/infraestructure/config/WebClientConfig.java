package com.selenium.selenium.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.selenium.selenium.infraestructure.webclient.WebClientBuilder;

/**
 * @author Gustavo Rubin
 *
 */
@Configuration
public class WebClientConfig {

    @Value("${application.proxy.host}")
    private String host;
    @Value("${application.proxy.port}")
    private int port;

    @Bean
    WebClient getWebClient() {
	return WebClientBuilder.build(host, port);
    }

}
