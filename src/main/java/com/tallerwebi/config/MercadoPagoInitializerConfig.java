package com.tallerwebi.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
public class MercadoPagoInitializerConfig {

    @Bean
    public void initializeMercadoPagoAccesToken() {
        MercadoPagoConfig.setAccessToken("APP_USR-4056231313034055-070822-e86fb2056a2461743a3744a07fc4e2c7-2547024138");
    }
}
