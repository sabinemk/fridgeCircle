package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.Recipe;
import com.hiFive.FridgeCircle.entity.RecipeRequest;
import com.hiFive.FridgeCircle.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;

/* TODO Endpoints for RecipeController:
 - /recipes @get
 - /recipe @get/@post
 */
@Controller
public class RecipeController {
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String showAllRecipesPage() {
        return "recipes";

    }

    @GetMapping("/recipe")
    public String showRecipePage() {
        return "recipe";
    }
    @PostMapping("/recipe")
    public String handleRecipeCreation(RecipeRequest recipeRequest){
       try {
           this.recipeService.createRecipe(new Recipe(
                   recipeRequest.getName(),
                   recipeRequest.getDifficultyLevel(),
                   Integer.valueOf(recipeRequest.getRating()),
                   recipeRequest.getCookingTime(),
                   Integer.valueOf(recipeRequest.getPortionSize()),
                   recipeRequest.getCookingSteps(),
                   new BigInteger(String.valueOf(332233))));

           return "redirect:recipes?status=RECIPE-CREATE_SUCCESS";
       }
       catch (Exception exception){
           exception.printStackTrace();
           return "redirect:recipe?status=RECIPE-CREATE_FAILED&message=" + exception.getMessage();
        }
    }

}
