package br.com.customer.orders.infrastructure.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    @Override
    public LocalDateTime convert(String source) {
        var offsetDateTime = OffsetDateTime.parse(source, FORMATTER);
        return offsetDateTime.toLocalDateTime();
    }
}