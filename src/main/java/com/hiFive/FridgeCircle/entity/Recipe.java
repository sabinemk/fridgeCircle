package com.hiFive.FridgeCircle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Difficulty difficultyLevel;
    private Integer rating;
    private Integer cookingTime;
    private Integer portionSize;
    private String cookingSteps;
    @OneToMany
    private List<RecipeIngredient> ingredientList;
    private Long creatorId;
    @OneToMany
    private List<Tag> tagsList;

    public Recipe(String name, Difficulty difficultyLevel, Integer rating,
                  Integer cookingTime, Integer portionSize,
                  String cookingSteps, List<RecipeIngredient> ingredientList, Long creatorId, List<Tag> tags) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
        this.cookingTime = cookingTime;
        this.portionSize = portionSize;
        this.cookingSteps = cookingSteps;
        this.ingredientList=ingredientList;
        this.creatorId = creatorId;
        this.tagsList=tags;
    }
}

