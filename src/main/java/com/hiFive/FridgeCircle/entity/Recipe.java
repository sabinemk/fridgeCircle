package com.hiFive.FridgeCircle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String difficultyLevel;
    private Integer rating;
//    private ArrayList<Ingridient> Ingredients;
    private String cookingTime;
    private Integer portionSize;
    //private ArrayList<SpecialTag> specialTags (vegan/veggie etc);
    private String cookingSteps;
    private BigInteger authorId;

    public Recipe(String name, String difficultyLevel, Integer rating, String cookingTime, Integer portionSize, String cookingSteps, BigInteger authorId) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
        this.cookingTime = cookingTime;
        this.portionSize = portionSize;
        this.cookingSteps = cookingSteps;
        this.authorId = authorId;
    }
}

