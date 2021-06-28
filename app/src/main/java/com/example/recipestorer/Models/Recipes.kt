package com.example.recipestorer.Models

import com.google.firebase.database.Exclude
import java.sql.Time
import java.time.LocalTime

data class Recipes(
    val recipeImageString: String="",
    val recipeTitle: String ="",
    val recipeIngredients: String="",
    val recipeInstructions: String="",
    val recipeDescription: String="",
    val recipeServingSize: String= "",
    val numOfIngredients: String  = "",
    val recipeStepsCount: Int = 0,
    @Exclude val uid: String? = null)


//can u store other types to database or need to convert?

// recipe type (breakfast, lunch, dinner) BOOLEAN? STRING?


// recipe Time taken TIME??? STRING?



//if yes how to get...

//else how to convert to string?
