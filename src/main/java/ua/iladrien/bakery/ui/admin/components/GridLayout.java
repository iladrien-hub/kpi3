package ua.iladrien.bakery.ui.admin.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class GridLayout extends VerticalLayout {

    private final int columns;

    private int cursor = 0;

    private HorizontalLayout horizontalLayout;

    public GridLayout(int columns) {
        this.columns = columns;
        setAlignItems(Alignment.CENTER);

        initHorizontalLayout();
    }

    private void initHorizontalLayout() {
        horizontalLayout = new HorizontalLayout();
        add(horizontalLayout);
    }

    public void addComponent(Component component) {
        horizontalLayout.add(component);
        cursor += 1;
        if (cursor == columns) {
            cursor = 0;
            initHorizontalLayout();
        }
    }
}
