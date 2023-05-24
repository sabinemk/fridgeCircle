package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Integer>{

        Recipe findByNameContainsIgnoreCase(String recipeName);

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
