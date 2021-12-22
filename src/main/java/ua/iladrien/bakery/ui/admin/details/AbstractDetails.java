package ua.iladrien.bakery.ui.admin.details;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import lombok.SneakyThrows;
import ua.iladrien.bakery.web.api.v0.IService;
import ua.iladrien.bakery.web.entities.IEntity;

public abstract class AbstractDetails<EntityType extends IEntity, ServiceType extends IService<EntityType>>
        extends VerticalLayout implements BeforeEnterObserver {

    protected EntityType entity;
    private final Class<EntityType> typeClass;

    private final ServiceType mainService;

    protected final Button save = new Button("Save");
    protected final Button delete = new Button("Delete");
    protected final Button close = new Button("Close");

    protected final Binder<EntityType> binder;

    public AbstractDetails(Class<EntityType> typeClass, ServiceType mainService) {
        this.typeClass = typeClass;
        this.mainService = mainService;
        binder = new BeanValidationBinder<>(typeClass);
    }

    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String sid = beforeEnterEvent.getRouteParameters().get("id").orElseThrow();
        if (sid.equals("new")) {
            entity = typeClass.getDeclaredConstructor().newInstance();
        } else {
            Integer id = Integer.valueOf(sid);
            entity = mainService.findById(id);
        }
        if (entity != null) {
            initHeader();
            initContent();
            binder.bindInstanceFields(this);
            binder.setBean(entity);
        }
    }

    private void initHeader() {
        setSizeFull();
        addClassName("product-detail-view");
        H1 h1 = entity.getId() == null ?
                new H1("New " + typeClass.getSimpleName()) : new H1(typeClass.getSimpleName() + " #" + entity.getId());
        add(h1);
    }

    protected Component initButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(evt -> save());
        close.addClickListener(evt -> close());
        delete.addClickListener(evt -> delete());

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    protected abstract void initContent();
    protected abstract void save();
    protected abstract void close();
    protected abstract void delete();

}
