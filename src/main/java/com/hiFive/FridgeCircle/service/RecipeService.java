package com.hiFive.FridgeCircle.service;

import com.hiFive.FridgeCircle.entity.Recipe;
import com.hiFive.FridgeCircle.repository.RecipeRepository;
import com.hiFive.FridgeCircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    RecipeRepository recipeRepository;
    @Autowired
    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }

    public void createRecipe(Recipe recipeToCreate){
        this.recipeRepository.save(recipeToCreate);
    }
    public Recipe findByNamePart(String recipeNamePart){
       return recipeRepository.findByNameContainsIgnoreCase(recipeNamePart);
    }

            /* TODO Recipe find options should be applied to:
         - find by name
         - find by difficulty level
         - find by rating
         - find by cookingTime
         - find by author

         - find by ingredients
         - find by tags

         */

}
