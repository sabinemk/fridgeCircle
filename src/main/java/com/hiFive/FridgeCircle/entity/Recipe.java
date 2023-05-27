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
    private String difficultyLevel;
    private Integer rating;
//    @OneToOne
/*    @ManyToMany
   @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))*/
 //   private Ingredient ingredients;
    private String cookingTime;
    private Integer portionSize;
//    @OneToOne
   /* @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))*/
//    private Tag specialTags; //(vegan/veggie etc);
    private String cookingSteps;

    private Long creatorId;
//    @OneToOne
    private String tag;

    public Recipe(String name, String difficultyLevel, Integer rating,
                  String cookingTime, Integer portionSize,
                  String cookingSteps, Long creatorId, String tag) {
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.rating = rating;
        this.cookingTime = cookingTime;
        this.portionSize = portionSize;
        this.cookingSteps = cookingSteps;
        this.creatorId = creatorId;
        this.tag=tag;
    }
}

