package ua.iladrien.bakery.web.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
    @NotEmpty
    private String sessionPushId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    private OrderData order;
}
