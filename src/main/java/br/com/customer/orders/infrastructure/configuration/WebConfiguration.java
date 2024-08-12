package br.com.customer.orders.infrastructure.configuration;

import br.com.customer.orders.infrastructure.converter.LocalDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final LocalDateTimeConverter converter;

    public WebConfiguration(LocalDateTimeConverter converter) {
        this.converter = converter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateTimeConverter());
    }
}