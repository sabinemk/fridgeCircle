package com.hiFive.FridgeCircle.service;

import com.hiFive.FridgeCircle.entity.RecipeIngredient;
import com.hiFive.FridgeCircle.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeIngredientService {
    RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository=recipeIngredientRepository;
    }

    public void createRecipeIngredient(RecipeIngredient recipeIngredient){
        this.recipeIngredientRepository.save(recipeIngredient);
    }

    public void saveRecipeIngredientList(List<RecipeIngredient> recipeIngredientList){
        this.recipeIngredientRepository.saveAll(recipeIngredientList);
    }

}
