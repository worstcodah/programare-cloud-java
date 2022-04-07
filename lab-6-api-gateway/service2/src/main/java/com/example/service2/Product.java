package com.example.service2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Product  {
    interface BasicValidation {
    }


    @NotNull(groups = BasicValidation.class)
    @Size(min = 3, max = 30, groups = BasicValidation.class)
    @Column(nullable = false, unique = true)
    private String name;


    @NotNull(groups = BasicValidation.class)
    @Size(min = 0, max = 100, groups = BasicValidation.class)
    @Column(nullable = false)
    private int quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, quantity);
    }

    @Override
    public String toString() {
        return String.format("Product[name='%s', quantity='%s']\n",
                name, quantity);

    }

}
