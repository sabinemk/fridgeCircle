package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.*;
import com.hiFive.FridgeCircle.exception.RecipeException;
import com.hiFive.FridgeCircle.repository.RecipeRepository;
import com.hiFive.FridgeCircle.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private RecipeRepository recipeRepository;
    private Recipe recipe;

    private FileStorageServiceImplementation fileStorageServiceImplementation;


    @Autowired
    public RecipeController(RecipeService recipeService,
                            TagService tagService,
                            IngredientService ingredientService,
                            RecipeIngredientService recipeIngredientService,
                            FileStorageServiceImplementation fileStorageServiceImplementation) {
        this.recipeService = recipeService;
        this.tagService = tagService;
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
        this.fileStorageServiceImplementation = fileStorageServiceImplementation;
    }

    @GetMapping("/recipes")
    public String showAllRecipesPage(Model model) {

        List<Recipe> allRecipes = this.recipeService.findAll();
        System.out.println("Find  ALL result");
        allRecipes.forEach(recipe -> System.out.println(recipe));
        model.addAttribute("recipeList", allRecipes);
        return "recipes";

    }


    @GetMapping("/recipes/search")
    public String searchRecipeNameTagIngredient(@RequestParam(required = false) String searchNavBar, Model model) {
        List<Recipe> searchedRecipes = this.recipeService.findAllByString(searchNavBar);
        System.out.println("Search results for: " + searchNavBar);

        searchedRecipes.forEach(recipe -> System.out.println(recipe));
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
        Recipe recipeToUpdate = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipeToUpdate);
        return "updateRecipe";
    }


    @PostMapping("/updaterecipe/{id}")
    public String updateRecipe(@PathVariable Long id, RecipeRequest recipeRequest, Model model) {
        Recipe recipeToUpdate = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipeToUpdate);
        recipeToUpdate.setDifficultyLevel(Difficulty.valueOf(recipeRequest.getDifficultyLevel().toUpperCase()));
        recipeToUpdate.setRating(Integer.parseInt(String.valueOf(recipeRequest.getRating())));
        recipeToUpdate.setCookingTime(recipeRequest.getCookingTime());
        recipeToUpdate.setPortionSize(Integer.parseInt(String.valueOf(recipeRequest.getPortionSize())));
        recipeToUpdate.setCookingSteps(recipeRequest.getCookingSteps());
        //ingredientList
        //Long.valueOf(recipeRequest.getCreator()),
        //tag11);

        this.recipeService.updateRecipe(recipeToUpdate);

        return "redirect:/recipe/" + id + "?status=RECIPE_UPDATED_SUCCESSFULLY";
    }

    @GetMapping("/deleterecipe/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        try {
            Recipe recipeToDelete = this.recipeService.getRecipeById(id);
            this.recipeService.deleteRecipe(recipeToDelete);
        } catch (RecipeException exception) {
            exception.getMessage();
        }
        return "redirect:/recipes?status=RECIPE_DELETED_SUCCESSFULLY";
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

    @GetMapping("/images/new/{id}")
    public String newImage(@PathVariable Long id, Model model) {
        System.out.println("new image controller");
        model.addAttribute("recipe", this.recipeService.getRecipeById(id));
        return "upload_form";
    }

    @PostMapping("/images/upload/{id}")
    public String uploadImage(@PathVariable Long id, Model model,
                              @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            Recipe recipe = this.recipeService.getRecipeById(id);
            model.addAttribute("recipe", recipe);
            System.out.println("Upload images controller");
            fileStorageServiceImplementation.save(file);
            recipe.setFileName(file.getOriginalFilename());
            recipe.setUrl(this.fileStorageServiceImplementation.getRoot().toString().substring(1) + "\\" + file.getOriginalFilename());
            this.recipeService.updateRecipe(recipe);
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);

        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            System.out.println(message);
            model.addAttribute("message", message);
        }

        return "upload_form";
    }

    @PostMapping("/createrecipe")
    public String handleRecipeCreation(RecipeRequest recipeRequest, Model model) {
        System.out.println(recipeRequest);
        try {

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
                    tag11,
                    "",
                    "");

            recipeService.createRecipe(recipe2);

            model.addAttribute("status", "success");
            model.addAttribute("message", "Recipe created sucessfully!");
            Long id = recipe2.getId();
            return "redirect:recipe/" + id + "?status=RECIPE-CREATE_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();

            model.addAttribute("status", "error");
            model.addAttribute("message", "Failed to create a recipe.." + exception.getMessage());

            return "redirect:recipes";
        }
    }

}
