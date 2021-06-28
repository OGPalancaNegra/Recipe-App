package com.example.recipestorer

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.text.Layout
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.recipestorer.Adapters.PersonalRecipeAdapter
import com.example.recipestorer.Fragments.ProfileFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activty_view_recipes.*

class DisplayRecipes: AppCompatActivity(), PersonalRecipeAdapter.OnItemClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_view_recipes)


        backicon.setOnClickListener {
            finish()
        }

        //val intentTing= intent.getParcelableExtra<Adapter>(ProfileFragment.INTENT_PARCELABLE)

        val recipeImage = findViewById<ImageView>(R.id.recipe_image)

        //Picasso.get().load(R.drawable.ic_round_android_24).placeholder(R.drawable.ic_round_android_24).fit().centerCrop().into(recipeImage)

        val recipeTitle = findViewById<TextView>(R.id.recipe_title)

        val recipeDescription = findViewById<TextView>(R.id.recipe_description)
        val recipeInstruction = findViewById<TextView>(R.id.recipe_instructions)
        val recipeIngredients = findViewById<TextView>(R.id.recipe_ingredients)
        val recipeIngredientCount = findViewById<TextView>(R.id.recipe_ingredient_count)
        val recipeStepCount = findViewById<TextView>(R.id.recipe_step_count_textview)
        val recipeServingSize = findViewById<TextView>(R.id.recipe_serving_size_view)
        val recipeCreator = findViewById<TextView>(R.id.recipe_chef)


        //recipeTitle.text = intentTing.
        val recipeTitleString = intent.getStringExtra("recipe_title")
        val recipeDescriptionString = intent.getStringExtra("recipe_description")
        val recipeIngredientsString = intent.getStringExtra("recipe_ingredients")
        val recipeInstructionString = intent.getStringExtra("recipe_instructions")
        val recipeImageString = intent.getStringExtra("recipe_image")
        val recipeIngredientCountString = intent.getStringExtra("recipe_ingredient_count")
        val recipeStepCountString = intent.getStringExtra("recipe_steps_count")
        val recipeServingSizeString = intent.getStringExtra("recipe_serving_size")!!

        val recipeCreatorString = intent.getStringExtra("recipe_creator")

        recipeTitle.text = recipeTitleString
        recipeDescription.text = recipeDescriptionString
        recipeIngredients.text = recipeIngredientsString
        recipeInstruction.text = recipeInstructionString
        Picasso.get().load(recipeImageString).fit().centerCrop().into(recipeImage)
        recipeIngredientCount.text = recipeIngredientCountString
        recipeStepCount.text = recipeStepCountString
        recipeServingSize.text = recipeServingSizeString

        recipeCreator.text=recipeCreatorString





    }





}