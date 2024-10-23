package com.nc13.moviemates.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextListener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

@Configuration
public class ListenerConfig implements WebApplicationInitializer {
    Logger log = LoggerFactory.getLogger(ListenerConfig.class);
    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.addListener(new RequestContextListener());
        log.info("=============== ListenerConfig onStartup =========================");
    }
}