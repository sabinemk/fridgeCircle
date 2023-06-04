package com.hiFive.FridgeCircle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String name;
    private Difficulty difficultyLevel;
    private Integer rating;
    private Integer cookingTime;
    private Integer portionSize;
    private RecipeIngredient recipeIngredientList;
//    private String ingredient;
//    private String quantity;
//    private String unit;
    private String cookingSteps;
    private String creator;
    private List<Tag> tags;
}
