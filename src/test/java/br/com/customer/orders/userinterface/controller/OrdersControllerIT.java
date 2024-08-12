package br.com.customer.orders.userinterface.controller;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("it")
@DisplayName("OrdersControllerIT")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrdersControllerIT {
    private static final String feature = "classpath:features/orders.feature";
    
    @LocalServerPort
    private Integer port;

    @Value("${spring.security.user.token}")
    private String token;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void before() {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("server.application", "http://localhost:" + port);
        System.setProperty("server.authorization", token);
    }

    @Karate.Test
    @DisplayName("OrdersController.features")
    Karate features() {
        return Karate.run(feature).relativeTo(getClass());
    }
}
