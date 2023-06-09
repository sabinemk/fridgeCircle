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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<RecipeIngredient> ingredientList;
    private Long creatorId;
    @OneToOne
    private Tag tag;
    private String fileName;
    private String url;

    public Recipe(String name, Difficulty difficultyLevel, Integer rating,
                  Integer cookingTime, Integer portionSize,
                  String cookingSteps, List<RecipeIngredient> ingredientList, Long creatorId, Tag tag,String fileName,String url) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
        this.cookingTime = cookingTime;
        this.portionSize = portionSize;
        this.cookingSteps = cookingSteps;
        this.ingredientList=ingredientList;
        this.creatorId = creatorId;
        this.tag=tag;
        this.fileName=fileName;
        this.url=url;
    }
}

