package me.keills.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.keills.exception.OnLoadFailedException;
import me.keills.exception.OnSaveFailedException;
import me.keills.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class RecipesRepo {

    private JdbcTemplate jdbcTemplate;
    private RowMapper mapper = (rs, rowNum) -> {
        try {
            return new Recipe(rs.getString("name"),
                    new ObjectMapper().readValue(rs.getString("ingredients"), HashMap.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };

    @Autowired
    public RecipesRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS recipes (name varchar(255) UNIQUE, ingredients text)");
    }

    public void save(Recipe recipe) throws JsonProcessingException, OnSaveFailedException{
        String sql = "INSERT INTO recipes (name, ingredients) VALUES (?, ?)";
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(recipe.getIngredients());
        try {
            jdbcTemplate.update(sql, recipe.getName(), jacksonData);
        } catch (DataAccessException e){
            throw new OnSaveFailedException(e.getMessage());
        }
    }

    public Recipe findByName(String name) throws JsonProcessingException, OnLoadFailedException {
        String sql = "SELECT * FROM recipes WHERE name = ?";
        try{
            return (Recipe) jdbcTemplate.queryForObject(sql, mapper, name);
        } catch (DataAccessException e){
            throw new OnLoadFailedException(e.getMessage());
        }
    }

    public Recipe findByIngredients(Map<String, String> ingredients) throws JsonProcessingException, OnLoadFailedException {
        StringBuilder sql = new StringBuilder("SELECT * FROM recipes WHERE ingredients LIKE ?");
        for (int i = 1; i < ingredients.size(); i++) {
            sql.append(" OR ingredients LIKE ?");
        }
        List<String> ingredientsList = ingredients.keySet().stream().map(ingredient -> "%" + ingredient + "%").collect(Collectors.toList());
        try{
            return (Recipe) jdbcTemplate.queryForObject(sql.toString(),mapper,ingredientsList.toArray());
        } catch (DataAccessException e){
            throw new OnLoadFailedException(e.getMessage());
        }
    }
}
