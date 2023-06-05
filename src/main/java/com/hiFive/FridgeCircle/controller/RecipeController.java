package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.*;
import com.hiFive.FridgeCircle.service.IngredientService;
import com.hiFive.FridgeCircle.service.RecipeIngredientService;
import com.hiFive.FridgeCircle.service.RecipeService;
import com.hiFive.FridgeCircle.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/* TODO Endpoints for RecipeController:
 - /recipes @get
 - /recipe @get/@post

 UPDATED:
 http://localhost/createrecipe  /fill form - from form recipe will not be created because of Lists,
                                 but "chicken" is harcoded after "submit" button,
                                 then you can access recipe - recipe.html I coppied from createrecpy.
                                 It has to display selected recipe - I pass in controller showOneRecipe
                                 model three objects which can be used for display in "recipe" page.
 http://localhost/recipe/1              */
@Controller
public class RecipeController {
    private RecipeService recipeService;
    private TagService tagService;
    private IngredientService ingredientService;

    private RecipeIngredientService recipeIngredientService;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            TagService tagService,
                            IngredientService ingredientService,
                            RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.tagService = tagService;
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
    }

    @GetMapping("/recipes")
    public String showAllRecipesPage(Model model) {
        List<Recipe> allRecipes = this.recipeService.findAll();
        System.out.println("Find  ALL result");
        allRecipes.forEach(recipe->System.out.println(recipe));
        model.addAttribute("recipeList", allRecipes);
        return "recipes";

    }
//    @GetMapping("/recipes")
//    public String showAllRecipesPage(@RequestParam(required = false) String search, Model model) {
//        List<Recipe> recipeList;
//        if (search != null && !search.isEmpty()) {
//            recipeList = (List<Recipe>) recipeService.findByNamePart(search);
//        } else {
//            recipeList = recipeService.findAll();
//        }
//        model.addAttribute("recipeList", recipeList);
//        return "recipes";
//    }



    @GetMapping("/recipes/{searchString}")
    public String searchRecipeNameTagIngredient(@PathVariable(required = false) String searchString, Model model) {
        List<Recipe> searchedRecipes = this.recipeService.findAllByString(searchString);
        System.out.println("Search results for: " + searchString);
        searchedRecipes.forEach(recipe->System.out.println(recipe));
        model.addAttribute("recipeList", searchedRecipes);
        // search Recipe by Name by Tag by Ingredient and add to list
        //model pass List to html
        return "recipes";
    }


    @GetMapping("/recipe/{id}")
    public String showOneRecipe(@PathVariable Long id, Model model) {
        Recipe foundRecipe = this.recipeService.getRecipeById(id);
        model.addAttribute("recipe", foundRecipe);
        model.addAttribute("ingredientList", foundRecipe.getIngredientList());


        return "recipe";

    }

   @GetMapping("/updaterecipe/{id}")
          public String updateRecipe(@PathVariable Long id, Model model) {
              Recipe recipeToUpdate=recipeService.getRecipeById(id);
              model.addAttribute("recipe", recipeToUpdate);
              return "updateRecipe";
          }


          //my idea was to just show the recipe according to a search. maybe it would
          //be easier for accessing the database? instead of the various tags.

    @PostMapping("/updaterecipe/{id}")
    public String updateRecipe(@PathVariable Long id, RecipeRequest recipeRequest, Model model){
            Recipe recipeToUpdate=recipeService.getRecipeById(id);
            model.addAttribute("recipe", recipeToUpdate);
            return "redirect:recipe/"+id+"?status=RECIPE_UPDATED_SUCCESSFULLY";
        }

    @GetMapping("/createrecipe")
    public String showRecipePage(Model model) {
        //get difficulty level enum
        //get ingredients list entity
        //get tags entity
        //get units to display enum

        //List<Ingredient> ingredientService.getAll();
        //List<Tag>tagService.getAll;
        //model.addAttribute("ingrList",ingredientList);
        //if ingredient is not in list? "Other"
        return "createRecipe";
    }

    @PostMapping("/createrecipe")
    public String handleRecipeCreation(RecipeRequest recipeRequest, Model model) {
        try {
           /*---manual creation of recipe:
            1.Create Ingredients in database.
            2.Create Tag in database and Tag list.
            3.Create RecipeIngredients for a recipe in database and List.
            4.Create Recipe with RecipeIngredients and Tag list in database.
            ---*/
            List<Tag> specialTags = new ArrayList<>();
            List<RecipeIngredient> recIngrList = new ArrayList<>();

            Ingredient ingredient1 = new Ingredient("milk");
            Ingredient ingredient2 = new Ingredient("flour");
            Ingredient ingredient3 = new Ingredient("water");
            Ingredient ingredient4 = new Ingredient("egg");

            Tag tag1 = new Tag("non-vegan");

            this.ingredientService.createIngredient(ingredient1);
            this.ingredientService.createIngredient(ingredient2);
            this.ingredientService.createIngredient(ingredient3);
            this.ingredientService.createIngredient(ingredient4);

            this.tagService.createTag(tag1);

            RecipeIngredient recIngr1 = new RecipeIngredient(ingredient1, 6, Unit.CUP);
            RecipeIngredient recIngr2 = new RecipeIngredient(ingredient2, 300, Unit.GRAMS);
            RecipeIngredient recIngr3 = new RecipeIngredient(ingredient3, 100, Unit.GRAMS);
            RecipeIngredient recIngr4 = new RecipeIngredient(ingredient4, 3, Unit.PIECE);

            recIngrList.add(recIngr1);
            recIngrList.add(recIngr2);
            recIngrList.add(recIngr3);
            recIngrList.add(recIngr4);

            this.recipeIngredientService.saveRecipeIngredientList(recIngrList);

            Recipe recipe1 = new Recipe(
                    "Chicken",
                    Difficulty.EASY,
                    10,
                    15,
                    9,
                    "1. Mix" +
                            " 2.Cook ",
                    recIngrList,
                    332233L,
                    tag1);
            this.recipeService.createRecipe(recipe1);

           /*----RecipeRequest----*

                       1.Create Ingredients in database.
                       2.Create Tag in database and Tag list.
                       3.Create RecipeIngredients for a recipe in database and List.
                       4.Create Recipe with RecipeIngredients and Tag list in database.      */

            List<RecipeIngredient> recipeIngredientList = new ArrayList<>();

            model.addAttribute("ingredientList", recipeIngredientList);

            Ingredient ingredient11 = new Ingredient(recipeRequest.getIngredient());
            ingredientService.createIngredient(ingredient11);

            RecipeIngredient recIngr11 = new RecipeIngredient(ingredient11,
                    recipeRequest.getQuantity(), Unit.valueOf(recipeRequest.getUnit().toUpperCase()));
            recipeIngredientList.add(recIngr11);
            recipeIngredientService.createRecipeIngredient(recIngr11);

            Tag tag11 = new Tag(recipeRequest.getTag());
            tagService.createTag(tag11);
            Recipe recipe2 = new Recipe(
                    recipeRequest.getName(),
                    Difficulty.valueOf(recipeRequest.getDifficultyLevel().toUpperCase()),
                    Integer.parseInt(String.valueOf(recipeRequest.getRating())),
                    recipeRequest.getCookingTime(),
                    Integer.parseInt(String.valueOf(recipeRequest.getPortionSize())),
                    recipeRequest.getCookingSteps(),
                    recipeIngredientList,
                    Long.valueOf(recipeRequest.getCreator()),
                    tag11);

            recipeService.createRecipe(recipe2);

            model.addAttribute("status", "success");
            model.addAttribute("message", "Recipe created sucessfully!");

            return "redirect:recipes?status=RECIPE-CREATE_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();

            model.addAttribute("status", "error");
            model.addAttribute("message", "Failed to create a recipe.."+ exception.getMessage());

            return "redirect:recipes";
        }
    }

}
