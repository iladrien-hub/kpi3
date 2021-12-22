package ua.iladrien.bakery.web.api.v0.orders.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.iladrien.bakery.web.api.v0.orders.repos.CartItemRepository;
import ua.iladrien.bakery.web.api.v0.orders.repos.CartRepository;
import ua.iladrien.bakery.web.api.v0.orders.repos.OrderRepository;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.OrderData;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart getCart(String sessionPushId) {
        Cart cart = cartRepository.findBySessionPushIdAndOrderIsNull(sessionPushId);
        if (cart == null) {
            cart = new Cart();
            cart.setSessionPushId(sessionPushId);
            cartRepository.save(cart);
        }
        return cart;
    }

    public void saveCartItem(CartItem item) {
        item.setPrice(item.getProduct().getPrice());
        cartItemRepository.save(item);
    }

    public void addToCart(Cart cart, CartItem item) {
        saveCartItem(item);
        cart.getItems().add(item);
        cartRepository.save(cart);
    }

    public Cart deleteCartItem(Cart cart, CartItem item) {
        cart.getItems().remove(item);
        cartRepository.save(cart);
        cartItemRepository.delete(item);
        return getCart(cart.getSessionPushId());
    }

    public void saveOrder(OrderData order, Cart cart) {
        cart.setOrder(order);
        order.setCreated(new Timestamp(System.currentTimeMillis()));
        order.setStatus(OrderData.OrderStatus.UNCONFIRMED);
        orderRepository.save(order);
        cartRepository.save(cart);
    }

    public Iterable<OrderData> findOrders(OrderData.OrderStatus value) {
        if (value != null) {
            return orderRepository.findAllByStatus(value);
        }
        return orderRepository.findAll();
    }

    public Cart findCart(OrderData value) {
        return cartRepository.findByOrder(value);
    }

    public void saveOrder(OrderData order) {
        orderRepository.save(order);
    }

    public BigDecimal getPrice(Cart cart) {
        return cart.getItems().stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
