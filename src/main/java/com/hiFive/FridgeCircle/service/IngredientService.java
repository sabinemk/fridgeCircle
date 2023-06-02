package com.hiFive.FridgeCircle.service;

import com.hiFive.FridgeCircle.entity.Ingredient;
import com.hiFive.FridgeCircle.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    public void createIngredient(Ingredient ingredient){
        this.ingredientRepository.save(ingredient);
    }
}
