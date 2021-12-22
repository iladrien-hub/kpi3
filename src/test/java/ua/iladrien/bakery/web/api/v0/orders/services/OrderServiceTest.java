package ua.iladrien.bakery.web.api.v0.orders.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.api.v0.orders.repos.CartItemRepository;
import ua.iladrien.bakery.web.api.v0.orders.repos.CartRepository;
import ua.iladrien.bakery.web.api.v0.orders.repos.OrderRepository;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.OrderData;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {

    @MockBean public CartRepository cartRepository;
    @MockBean public OrderRepository orderRepository;
    @MockBean public CartItemRepository cartItemRepository;

    @Autowired
    public OrderService orderService;

    @Test
    void getCartNegative() {
        String sessionPushId = "session-id";
        when(cartRepository.findBySessionPushIdAndOrderIsNull(sessionPushId))
                .thenReturn(null);
        orderService.getCart(sessionPushId);

        verify(cartRepository, times(1)).save(ArgumentMatchers.any(Cart.class));
    }

    @Test
    void getCartPositive() {
        String sessionPushId = "session-id";
        when(cartRepository.findBySessionPushIdAndOrderIsNull(sessionPushId))
                .thenReturn(new Cart());
        orderService.getCart(sessionPushId);

        verify(cartRepository, times(0)).save(ArgumentMatchers.any(Cart.class));
    }

    @Test
    void saveCartItem() {
        CartItem item = Mockito.mock(CartItem.class);
        when(item.getProduct()).thenReturn(new ProductOption());

        orderService.saveCartItem(item);

        verify(item, times(1)).setPrice(ArgumentMatchers.any());
        verify(cartItemRepository, times(1)).save(item);
    }

    @Test
    void addToCart() {
        CartItem item = Mockito.mock(CartItem.class);
        ProductOption t = new ProductOption();
        BigDecimal price = new BigDecimal(20);
        t.setPrice(price);
        when(item.getProduct()).thenReturn(t);

        Cart cart = new Cart();
        orderService.addToCart(cart, item);

        verify(item, times(1)).setPrice(price);
        verify(cartItemRepository, times(1)).save(item);
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void findOrdersPositive() {
        orderService.findOrders(null);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findOrdersNegative() {
        orderService.findOrders(OrderData.OrderStatus.COMPLETED);
        verify(orderRepository, times(1)).findAllByStatus(OrderData.OrderStatus.COMPLETED);
    }

    @Test
    void getPricePositive() {
        Cart cart = new Cart();
        CartItem e1 = new CartItem();
        e1.setPrice(BigDecimal.valueOf(10));
        e1.setQuantity(2);
        e1.setId(1);
        CartItem e2 = new CartItem();
        e2.setPrice(BigDecimal.valueOf(15));
        e2.setQuantity(1);
        e1.setId(2);
        cart.setItems(Set.of(e1, e2));

        Assertions.assertThat(orderService.getPrice(cart)).isEqualTo(BigDecimal.valueOf(35));
    }

    @Test
    void getPriceNegative() {
        Cart cart = new Cart();
        cart.setItems(new HashSet<>());

        Assertions.assertThat(orderService.getPrice(cart)).isEqualTo(BigDecimal.ZERO);
    }





}
