package br.com.customer.orders.infrastructure.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static br.com.customer.orders.infrastructure.converter.LocalDateTimeConverter.FORMATTER;

@DisplayName("LocalDateTimeConverterTest")
class LocalDateTimeConverterTest {
    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();
    
    @Test
    @DisplayName("LocalDateTimeConverter.convert")
    void convert() {
        var source = "2024-08-10T20:13:20.048Z";
        var result = converter.convert(source);

        var expected = OffsetDateTime.parse(source, FORMATTER).toLocalDateTime();
        Assertions.assertEquals(expected, result);
    }
}
