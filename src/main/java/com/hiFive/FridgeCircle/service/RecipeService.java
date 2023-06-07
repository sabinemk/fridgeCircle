package com.hiFive.FridgeCircle.service;

import com.hiFive.FridgeCircle.entity.Recipe;
import com.hiFive.FridgeCircle.exception.RecipeException;
import com.hiFive.FridgeCircle.repository.RecipeRepository;
import com.hiFive.FridgeCircle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    RecipeRepository recipeRepository;
    List<Recipe> recipeList;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void createRecipe(Recipe recipeToCreate) {
        this.recipeRepository.save(recipeToCreate);
    }

    public void updateRecipe(Recipe recipeToUpdate){this.recipeRepository.save(recipeToUpdate);}

    public void deleteRecipe(Recipe recipe) throws RecipeException {
        this.recipeList=this.findAll();
        if(!this.recipeList.contains(recipe)){
            throw new RecipeException("Recipe was not found");
        }
        this.recipeRepository.delete(recipe);
    }

    public Recipe findByNamePart(String recipeNamePart) {
        return this.recipeRepository.findByNameContainsIgnoreCase(recipeNamePart);
    }

    public Recipe getRecipeById(Long id) {
        return this.recipeRepository.findById(id).get();
    }

    public List<Recipe> findAllByString(String searchString) {
        return this.recipeRepository.findAllByString(searchString);
    }

            /* TODO Recipe find options should be applied to:
         - find by name
         - find by difficulty level
         - find by rating
         - find by cookingTime
         - find by author

         - find by ingredients
         - find by tags */

    public List<Recipe> findAll() {
        return this.recipeRepository.findAll();
    }

}
