package com.tallerwebi.config;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class MercadoPagoInitializerConfig {

    @Autowired
    MercadoPagoConfig mercadoPagoConfig;
}
