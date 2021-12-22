package ua.iladrien.bakery.ui.shop.components;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class CoverImage extends VerticalLayout {

    public CoverImage(byte[] data, String alt, int width, int height) {
        Image image;
        if (data != null) {
            StreamResource rs = new StreamResource(alt, () -> new ByteArrayInputStream(data));
            rs.setContentType("image/png");
            image = new Image(rs, alt);
        } else {
            image = new Image("img/placeholder-image.jpg", alt);
        }

        image.setSizeFull();
        addClassName("cover-image");
        setWidth(width, Unit.PIXELS);
        setHeight(height, Unit.PIXELS);

        setSpacing(false);
        setPadding(false);

        add(image);
    }

}
