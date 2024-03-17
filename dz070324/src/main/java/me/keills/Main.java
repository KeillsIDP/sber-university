package me.keills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.keills.controller.RecipeFinderController;
import me.keills.dto.RecipeDto;
import me.keills.model.Recipe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        RecipeFinderController rfc = context.getBean(RecipeFinderController.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите 0 чтобы добавить рецепт");
            System.out.println("Введите 1 чтобы найти рецепт по имени");
            System.out.println("Введите 2 чтобы найти рецепт по ингредиентам");

            switch (scanner.nextInt()){
                case 0:{
                    scanner.nextLine();

                    System.out.println("Введите название рецепта");
                    String name = scanner.nextLine();
                    System.out.println("Введите кол-во ингредиентов");

                    int ingredientsAmount = scanner.nextInt();
                    scanner.nextLine();

                    Map<String,String> ingredients = new HashMap<>();
                    for(int i = 0; i<ingredientsAmount; i++){
                        System.out.println("Введите название ингредиента");
                        String ingredientName = scanner.nextLine();
                        System.out.println("Введите объем ингредиента");
                        String ingredientSize = scanner.nextLine();
                        ingredients.putIfAbsent(ingredientName, ingredientSize);
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jacksonData = null;
                    try {
                        jacksonData = objectMapper.writeValueAsString(ingredients);
                    } catch (JsonProcessingException e) {
                        System.out.println("Ты вписал что-то не то!");
                        break;
                    }
                    rfc.addRecipe(new Recipe(name, jacksonData));
                    break;
                }
                case 1:{
                    scanner.nextLine();
                    System.out.println("Введите название рецепта");
                    String name = scanner.nextLine();
                    rfc.findRecipe(name).ifPresentOrElse((r)-> System.out.println(RecipeDto.toModel(r)), () -> System.out.println("Рецепта с такими ингредиентами нет"));
                    break;
                }
                case 2:{
                    scanner.nextLine();
                    System.out.println("Введите кол-во ингредиентов");
                    int ingredientsAmount = scanner.nextInt();
                    scanner.nextLine();

                    Map<String,String> ingredients = new HashMap<>();
                    for(int i = 0; i<ingredientsAmount; i++){
                        System.out.println("Введите название ингредиента");
                        String ingredientName = scanner.nextLine();
                        System.out.println("Введите объем ингредиента");
                        String ingredientSize = scanner.nextLine();
                        ingredients.putIfAbsent(ingredientName, ingredientSize);
                    }
                    rfc.findRecipe(ingredients).ifPresentOrElse((r)-> System.out.println(RecipeDto.toModel(r)), () -> System.out.println("Рецепта с такими ингредиентами нет"));
                    break;
                }
                default:{
                    System.out.println("Данной команды нет в списке");
                }
            }
        }
    }
}