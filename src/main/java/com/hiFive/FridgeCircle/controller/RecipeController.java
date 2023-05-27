package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.*;
import com.hiFive.FridgeCircle.repository.IngredientRepository;
import com.hiFive.FridgeCircle.repository.RecipeIngredientRepository;
import com.hiFive.FridgeCircle.repository.TagRepository;
import com.hiFive.FridgeCircle.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* TODO Endpoints for RecipeController:
 - /recipes @get
 - /recipe @get/@post
 */
@Controller
public class RecipeController {
    private RecipeService recipeService;
    private TagRepository tagRepository;
    private IngredientRepository ingredientRepository;
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            TagRepository tagRepository,
                            IngredientRepository ingredientRepository,
                            RecipeIngredientRepository recipeIngredientRepository) {
        this.tagRepository=tagRepository;
        this.ingredientRepository=ingredientRepository;
        this.recipeService = recipeService;
        this.recipeIngredientRepository=recipeIngredientRepository;
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
           List<Ingredient> ingredients=new ArrayList<>();
           List<Tag> specialTags=new ArrayList<>();
           List<Recipe> recipes=new ArrayList<>();

           Ingredient ingredient1=new Ingredient("milk");
           Ingredient ingredient2=new Ingredient("flour");
           Ingredient ingredient3=new Ingredient("water");
           Ingredient ingredient4=new Ingredient("egg");
           ingredients.add(ingredient1);
           ingredients.add(ingredient2);
           ingredients.add(ingredient3);
           ingredients.add(ingredient4);

           Tag tag1=new Tag("vegan");
           specialTags.add(tag1);

           this.ingredientRepository.save(ingredient1);
           this.ingredientRepository.save(ingredient2);
           this.ingredientRepository.save(ingredient3);
           this.ingredientRepository.save(ingredient4);

           this.tagRepository.save(tag1);

           Recipe recipe1=new Recipe(
                   "Chicken",
                   "easy",
                   10,
                   "90",
                   9,
                   "1. Mix" +
                           " 2.Cook ",
                   332233L,
                   "non-vegan");
           this.recipeService.createRecipe(recipe1);
           RecipeIngredient ingr1=new RecipeIngredient(recipe1,
                   ingredient1,8,Unit.CUP);
           RecipeIngredient ingr2=new RecipeIngredient(recipe1,
                   ingredient3,8,Unit.LITTER);
           RecipeIngredient ingr3=new RecipeIngredient(recipe1,
                   ingredient2,8,Unit.GRAMMS);
           this.recipeIngredientRepository.save(ingr1);
           this.recipeIngredientRepository.save(ingr2);
           this.recipeIngredientRepository.save(ingr3);

           Recipe recipe2=new Recipe(
                   recipeRequest.getName(),
                   recipeRequest.getDifficultyLevel(),
                   Integer.parseInt(String.valueOf(recipeRequest.getRating())),
                   recipeRequest.getCookingTime(),
                   Integer.parseInt(String.valueOf(recipeRequest.getPortionSize())),
                   recipeRequest.getCookingSteps(),
                   Long.valueOf(recipeRequest.getCreator()),
                   recipeRequest.getTag());

           this.recipeService.createRecipe(recipe2);
           this.tagRepository.save(new Tag(recipeRequest.getTag()));
           Ingredient ingr7=new Ingredient(recipeRequest.getIngredient());
           this.ingredientRepository.save(ingr7);
           RecipeIngredient rec_ingr1=new RecipeIngredient(
                   recipe2,
                   ingr7,
                   Integer.parseInt(String.valueOf(recipeRequest.getQuantity())),
                   Unit.valueOf(String.valueOf(recipeRequest.getUnit()).toUpperCase()));

           this.recipeIngredientRepository.save(rec_ingr1);

           return "redirect:recipes?status=RECIPE-CREATE_SUCCESS";
       }
       catch (Exception exception){
           exception.printStackTrace();
           return "redirect:recipe?status=RECIPE-CREATE_FAILED&message=" + exception.getMessage();
        }
    }

}
