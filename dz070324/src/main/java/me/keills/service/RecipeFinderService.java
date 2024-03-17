package me.keills.service;

import me.keills.model.Recipe;

import java.util.Map;

public interface RecipeFinderService {
    Recipe findRecipe(String name);
    Recipe findRecipe(Map<String,String> ingredients);
    void addRecipe(Recipe recipe);
}
