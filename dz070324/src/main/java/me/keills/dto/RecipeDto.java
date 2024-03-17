package me.keills.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.keills.model.Recipe;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
@Data
public class RecipeDto {

    private String name;
    private Map<String, String> ingredients;
    public static RecipeDto toModel(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.name = recipe.getName();
        try {
            dto.ingredients = new ObjectMapper().readValue(recipe.getIngredients(), HashMap.class);
        } catch (JsonProcessingException e) {
            dto.ingredients = null;
            System.out.println("Не смогли прочитать ингредиенты, возможна ошибка!");
        }
        return dto;
    }
}
