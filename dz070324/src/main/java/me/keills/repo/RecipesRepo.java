package me.keills.repo;

import me.keills.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface RecipesRepo extends CrudRepository<Recipe,Long> {
    Recipe findByName(String name);
    Recipe findByIngredients(String ingredients);
}
