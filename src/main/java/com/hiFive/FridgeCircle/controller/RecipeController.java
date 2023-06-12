package com.hiFive.FridgeCircle.controller;

import com.hiFive.FridgeCircle.entity.*;
import com.hiFive.FridgeCircle.exception.RecipeException;
import com.hiFive.FridgeCircle.repository.RecipeRepository;
import com.hiFive.FridgeCircle.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
        this.fileStorageServiceImplementation=fileStorageServiceImplementation;
    }

    @GetMapping("/recipes")
    public String showAllRecipesPage(Model model) {
        List<Recipe> allRecipes = this.recipeService.findAll();
        System.out.println("Find  ALL result");
        allRecipes.forEach(recipe -> System.out.println(recipe));
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


    @GetMapping("/recipes")
    public String searchRecipeNameTagIngredient(@RequestParam(required = false) String searchString, Model model) {
        List<Recipe> searchedRecipes = this.recipeService.findAllByString(searchString);
        System.out.println("Search results for: " + searchString);
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


    //my idea was to just show the recipe according to a search. maybe it would
    //be easier for accessing the database? instead of the various tags.

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
    public String newImage(@PathVariable Long id,Model model){
        System.out.println("new image controller");
        model.addAttribute("recipe",this.recipeService.getRecipeById(id));
        return "upload_form";
    }
    @PostMapping("/images/upload/{id}")
    public String uploadImage(@PathVariable Long id, Model model,
                              @RequestParam("file")MultipartFile file)
    {
        String message = "";

        try {
            Recipe recipe=this.recipeService.getRecipeById(id);
            model.addAttribute("recipe",recipe);
            System.out.println("Upload images controller");
            fileStorageServiceImplementation.save(file);
            recipe.setFileName(file.getOriginalFilename());
            recipe.setUrl(this.fileStorageServiceImplementation.getRoot().toString().substring(1)+"\\"+file.getOriginalFilename());
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
           /*---manual creation of recipe:
            1.Create Ingredients in database.
            2.Create Tag in database and Tag list.
            3.Create RecipeIngredients for a recipe in database and List.
            4.Create Recipe with RecipeIngredients and Tag list in database.
            ---*/
           /* List<Tag> specialTags = new ArrayList<>();
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
                    tag1,
                    "image.jpg",
                    "/upload/");
            this.recipeService.createRecipe(recipe1);*/


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
            Long id=recipe2.getId();
            return "redirect:recipe/"+id+"?status=RECIPE-CREATE_SUCCESS";
        } catch (Exception exception) {
            exception.printStackTrace();

            model.addAttribute("status", "error");
            model.addAttribute("message", "Failed to create a recipe.."+ exception.getMessage());

            return "redirect:recipes";
        }
    }

}
