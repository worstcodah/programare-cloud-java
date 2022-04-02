package com.example.service2;

import com.example.service2.base.AbstractEntity;
import com.example.service2.util.DateProcessor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends AbstractEntity {
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
        if (!Objects.equals(id, product.id)) return false;
        return Objects.equals(name, product.name) &&
                Objects.equals(quantity, product.quantity);
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
