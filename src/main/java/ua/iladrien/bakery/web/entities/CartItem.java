package ua.iladrien.bakery.web.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
public class CartItem implements IEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @ManyToOne
    private ProductOption product;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
