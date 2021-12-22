package ua.iladrien.bakery.ui.shop.cards;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ua.iladrien.bakery.ui.shop.components.CoverImage;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;


public class CartItemCard extends HorizontalLayout {

    private OnDelete onDeleteListener;
    private CartItem cartItem;

    public CartItemCard(CartItem cartItem, OnDelete onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
        this.cartItem = cartItem;

        ProductOption option = cartItem.getProduct();
        Product product = option.getProduct();
        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        Div div = new Div();
        div.addClassName("spacer");

        add(
                new CoverImage(product.getImage(), product.getName(), 150, 150),
                new Label(product.getName() + " " + option.getTitle()),
                new Label(String.valueOf(option.getPrice())),
                new Label(String.valueOf(cartItem.getQuantity())),
                div,
                getDeleteButton()
        );
    }

    private Component getDeleteButton() {
        Button button = new Button("Remove");
        button.addClickListener(evt -> onDeleteListener.execute(cartItem));
        button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return button;
    }

    public interface OnDelete {
        void execute(CartItem cartItem);
    }
}
