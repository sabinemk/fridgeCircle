package com.hiFive.FridgeCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Ingredient ingredient;
    private Integer quantity;
    private Unit unit;


    public RecipeIngredient(Recipe recipe, Ingredient ingredient, int quantity, Unit unit) {
        this.recipe=recipe;
        this.ingredient=ingredient;
        this.quantity=quantity;
        this.unit=unit;
    }
}
