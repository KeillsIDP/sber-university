package me.keills.controller;

import me.keills.model.Recipe;
import me.keills.service.RecipeFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class RecipeFinderController {
    @Autowired
    private RecipeFinderService recipeFinderService;
    public Optional<Recipe> findRecipe(String name) {
        return Optional.of(recipeFinderService.findRecipe(name));
    }

    public Optional<Recipe> findRecipe(Map<String,String> ingredients) {
        return Optional.of(recipeFinderService.findRecipe(ingredients));
    }

    public void addRecipe(Recipe recipe) {
        recipeFinderService.addRecipe(recipe);
    }
}
