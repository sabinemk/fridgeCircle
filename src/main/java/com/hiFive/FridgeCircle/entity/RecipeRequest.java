package com.hiFive.FridgeCircle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    private String name;
    private String difficultyLevel;
    private Integer rating;
    private Integer cookingTime;
    private Integer portionSize;
//  private RecipeIngredient recipeIngredientList;
    private String ingredient;
    private Integer quantity;
    private String unit;
    private String cookingSteps;
    private String creator;
    private String tag;
}
