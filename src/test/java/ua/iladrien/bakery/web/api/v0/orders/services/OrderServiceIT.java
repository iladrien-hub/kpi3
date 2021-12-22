package ua.iladrien.bakery.web.api.v0.orders.services;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.entities.OrderData;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-dev.properties")
@Sql(scripts = {"/sql/category-before.sql", "/sql/product-before.sql", "/sql/other-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/other-after.sql", "/sql/product-after.sql", "/sql/category-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class OrderServiceIT {

    @Autowired
    public OrderService orderService;

    @Before
    public void setUp() {

    }

    @Test
    void getCart() throws Exception {
        Assertions.assertThat(orderService.getCart("asd")).isNotNull();
        Assertions.assertThat(orderService.getCart("8ec4f838-06a6-4f2b-9b9b-dc4ddb691d5e")).isNotNull();
    }

    @Test
    void findOrdersAndCart() {
        List<OrderData> orders = (List<OrderData>) orderService.findOrders(null);
        Assertions.assertThat(orders.size()).isEqualTo(1);
        Assertions.assertThat(orderService.findCart(orders.get(0)).getId()).isEqualTo(148);
    }

}