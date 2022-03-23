package com.unitbv.datasource;

import com.unitbv.model.Ingredient;
import com.unitbv.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MyRecipes {

    private List<Recipe> recipes;

    public MyRecipes() {

        recipes = Stream.of(
                new Recipe("firstRecipe", Arrays.asList(
                        new Ingredient("Milk", 500, "ml"),
                        new Ingredient("Meat", 1500, "g"),
                        new Ingredient("Tomatoes", 12, "pieces"),
                        new Ingredient("Fries", 650, "g"),
                        new Ingredient("Olive oil", 200, "ml")
                ))
        ).collect(Collectors.toList());
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public Optional<Recipe> findRecipeByName(String name){
        return recipes.stream().filter(r -> r.getName().equals(name)).findFirst();
    }

    public Recipe saveRecipe(Recipe recipe){
        findRecipeByName(recipe.getName()).ifPresent(i -> {
            throw new RuntimeException("Recipe with name " + i.getName() + " already exists!");
        });
        this.recipes.add(recipe);

        return recipe;
    }

    public List<Recipe> saveAllRecipes(List<Recipe> recipesToAdd){
        recipesToAdd.forEach(recipe -> {
            findRecipeByName(recipe.getName()).ifPresent(i -> {
                throw new RuntimeException("Recipe with name " + i.getName() + " already exists!");
            });
        });
        this.recipes.addAll(recipesToAdd);

        return recipesToAdd;
    }

    public Recipe updateIngredient(Recipe recipe){
        Recipe existingRecipe = findRecipeByName(recipe.getName()).orElseThrow(
                () -> new RuntimeException("Recipe with name " + recipe.getName() + " not found!"));
        int index = recipes.indexOf(existingRecipe);
        recipes.add(index, recipe);

        return recipe;
    }

    public boolean deleteIngredient(String name){
        Recipe recipe = findRecipeByName(name).orElseThrow(
                () -> new RuntimeException("Recipe with name " + name + " not found!"));

        return recipes.remove(recipe);
    }
}
