package com.hiFive.FridgeCircle.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String name;
    private String difficultyLevel;
    private Integer rating;
    //    private ArrayList<Ingridient> Ingredients;
    private String cookingTime;
    private Integer portionSize;
    private String ingredient;
    private String quantity;
    private String unit;
    //private ArrayList<SpecialTag> specialTags (vegan/veggie etc);
    private String cookingSteps;
    private String creator;
    private String tag;
}
