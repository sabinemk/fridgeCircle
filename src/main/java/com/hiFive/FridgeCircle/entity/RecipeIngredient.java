package com.hiFive.FridgeCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Ingredient ingredient;
    private Integer quantity;
    private Unit unit;

    public RecipeIngredient(Ingredient ingredient, int quantity, Unit unit) {
        this.ingredient=ingredient;
        this.quantity=quantity;
        this.unit=unit;
    }
}
