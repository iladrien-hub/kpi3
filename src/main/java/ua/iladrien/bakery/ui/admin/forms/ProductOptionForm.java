package ua.iladrien.bakery.ui.admin.forms;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ua.iladrien.bakery.web.entities.ProductOption;

public class ProductOptionForm extends FormLayout {

    private final TextField title = new TextField("Title");
    private final BigDecimalField price = new BigDecimalField("Price");

    private final Binder<ProductOption> binder = new BeanValidationBinder<>(ProductOption.class);

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Close");


    public ProductOptionForm() {
        binder.bindInstanceFields(this);

        add(title, price, initButtons());
    }

    public void setProductOption(ProductOption value) {
        binder.setBean(value);
    }

    private Component initButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(evt -> fireEvent(new ProductOptionForm.SaveEvent(this, binder.getBean())));
        delete.addClickListener(evt -> fireEvent(new ProductOptionForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(evt -> fireEvent(new ProductOptionForm.CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    // Events
    public abstract static class ProductOptionFormEvent extends ComponentEvent<ProductOptionForm> {
        private final ProductOption option;

        public ProductOptionFormEvent(ProductOptionForm source, ProductOption option) {
            super(source, false);
            this.option = option;
        }

        public ProductOption getOption() {
            return option;
        }
    }

    public static class SaveEvent extends ProductOptionFormEvent {
        public SaveEvent(ProductOptionForm source, ProductOption option) {
            super(source, option);
        }
    }

    public static class DeleteEvent extends ProductOptionFormEvent {
        public DeleteEvent(ProductOptionForm source, ProductOption option) {
            super(source, option);
        }
    }

    public static class CloseEvent extends ProductOptionFormEvent {
        public CloseEvent(ProductOptionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
