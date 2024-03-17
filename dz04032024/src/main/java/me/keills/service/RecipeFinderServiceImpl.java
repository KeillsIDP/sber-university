package me.keills.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.keills.model.Recipe;
import me.keills.repo.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeFinderServiceImpl implements RecipeFinderService{
    @Autowired
    private RecipesRepo recipesRepo;

    @Override
    public Recipe findRecipe(String name) throws JsonProcessingException, DataAccessException {
        Recipe recipe = recipesRepo.findByName(name);
        return recipe;
    }

    @Override
    public Recipe findRecipe(Map<String, String> ingredients) throws JsonProcessingException, DataAccessException {
        Recipe recipe = recipesRepo.findByIngredients(ingredients);
        return recipe;
    }

    @Override
    public void addRecipe(Recipe recipe) throws JsonProcessingException, DataAccessException {
        recipesRepo.save(recipe);
    }
}
