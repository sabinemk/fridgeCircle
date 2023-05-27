package com.hiFive.FridgeCircle.repository;

import com.hiFive.FridgeCircle.entity.RecipeIngredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient,Long> {
}
