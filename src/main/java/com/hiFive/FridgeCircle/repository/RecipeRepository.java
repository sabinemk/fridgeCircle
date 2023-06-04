package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.Recipe;
import com.hiFive.FridgeCircle.entity.RecipeIngredient;
import com.hiFive.FridgeCircle.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Recipe findByNameContainsIgnoreCase(String recipeName);

    List<Recipe> findAll();

    default List<Recipe> findAllByString(String searchString) {
        List<Recipe> allRecipes = this.findAll();
        List<Recipe> foundRecipes = new ArrayList<>();
        String searchStringNoCase=searchString.toUpperCase();

        for (Recipe recipe : allRecipes) {
            List<RecipeIngredient> recipeIngredientList = recipe.getIngredientList();


            if (recipe.getName().toUpperCase().contains(searchStringNoCase)) {
                foundRecipes.add(recipe);
                continue;
            }
            recipeIngredientList.forEach(recipeIngredient ->
            {
                if (recipeIngredient.getIngredient().getName().toUpperCase().contains(searchStringNoCase)
                        && !foundRecipes.contains(recipe)) {
                    foundRecipes.add(recipe);
                    return;
                }

            });
            if (recipe.getTag().getName().toUpperCase().contains(searchStringNoCase) && !foundRecipes.contains(recipe)) {
                    foundRecipes.add(recipe);
                }
            };
        return foundRecipes;
    }

}

        /* TODO Recipe find options should be applied to:
         - find by name
         - find by ingredients
         - find by tags

         */


