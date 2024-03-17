package me.keills.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.keills.exception.OnLoadFailedException;
import me.keills.model.Recipe;
import me.keills.service.RecipeFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;

@Controller
public class RecipeFinderController {
    @Autowired
    private RecipeFinderService recipeFinderService;
    private final String JSON_EXCEPTION_MESSAGE = "Проблема с сохранением данных, проверьте не находится ли различных не буквенных символов в ингредиентах.";
    private final String ON_LOAD_EXCEPTION_MESSAGE = "Проблема загрузки данных, возможно вы пытаетесь получить не существующую запись или нарушили формат ввода данных.";
    private final String ON_SAVE_EXCEPTION_MESSAGE = "Не удалось сохранить запись, проверьте правильность введенных данных и отсутствие записи с данным именем и ингредиентами.";
    public Optional<Recipe> findRecipe(String name) {
        try {
            return Optional.of(recipeFinderService.findRecipe(name));
        } catch (JsonProcessingException e) {
            System.out.println(JSON_EXCEPTION_MESSAGE);
        } catch (DataAccessException e) {
            System.out.println(ON_LOAD_EXCEPTION_MESSAGE);
        }
        return Optional.empty();
    }

    public Optional<Recipe> findRecipe(Map<String,String> ingredients) {
        try {
            return Optional.of(recipeFinderService.findRecipe(ingredients));
        } catch (JsonProcessingException e) {
            System.out.println(JSON_EXCEPTION_MESSAGE);
        } catch (OnLoadFailedException e) {
            System.out.println(ON_LOAD_EXCEPTION_MESSAGE);
        }
        return Optional.empty();
    }

    public void addRecipe(Recipe recipe) {
        try {
            recipeFinderService.addRecipe(recipe);
        } catch (JsonProcessingException e) {
            System.out.println(JSON_EXCEPTION_MESSAGE);
        } catch (OnLoadFailedException e) {
            System.out.println(ON_SAVE_EXCEPTION_MESSAGE);
        }
    }
}
