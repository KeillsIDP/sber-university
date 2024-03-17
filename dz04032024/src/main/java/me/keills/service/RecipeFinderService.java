package me.keills.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.keills.exception.OnLoadFailedException;
import me.keills.exception.OnSaveFailedException;
import me.keills.model.Recipe;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface RecipeFinderService {
    Recipe findRecipe(String name) throws JsonProcessingException, OnSaveFailedException;
    Recipe findRecipe(Map<String,String> ingredients) throws JsonProcessingException, OnLoadFailedException;
    void addRecipe(Recipe recipe) throws JsonProcessingException, OnLoadFailedException;
}
