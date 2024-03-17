package me.keills;

import me.keills.controller.RecipeFinderController;
import me.keills.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
                    rfc.addRecipe(new Recipe(name, ingredients));
                    break;
                }
                case 1:{
                    scanner.nextLine();
                    System.out.println("Введите название рецепта");
                    String name = scanner.nextLine();
                    rfc.findRecipe(name).ifPresentOrElse(System.out::println, () -> System.out.println("Рецепта с такими ингредиентами нет"));
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
                    rfc.findRecipe(ingredients).ifPresentOrElse(System.out::println, () -> System.out.println("Рецепта с такими ингредиентами нет"));
                    break;
                }
                default:{
                    System.out.println("Данной команды нет в списке");
                }
            }
        }
    }
}