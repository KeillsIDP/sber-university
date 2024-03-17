package me.keills.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.keills.model.Recipe;
import me.keills.repo.RecipesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeFinderServiceImpl implements RecipeFinderService{
    @Autowired
    private RecipesRepo recipesRepo;

    @Override
    public Recipe findRecipe(String name) {
        Recipe recipe = recipesRepo.findByName(name);
        return recipe;
    }

    @Override
    public Recipe findRecipe(Map<String, String> ingredients) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = null;
        try {
            jacksonData = objectMapper.writeValueAsString(ingredients);
        } catch (JsonProcessingException e) {
            System.out.println("Ты вписал что-то не то!");
            return null;
        }
        Recipe recipe = recipesRepo.findByIngredients(jacksonData);
        return recipe;
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipesRepo.save(recipe);
    }
}
