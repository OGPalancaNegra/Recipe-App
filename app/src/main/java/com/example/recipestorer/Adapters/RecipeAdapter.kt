package com.example.recipestorer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import com.squareup.picasso.Picasso

class RecipeAdapter(val context: Context, val mRecipe: ArrayList<Recipes>, var isFragment: Boolean = false): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {


    class  ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var titleTextView = itemView.findViewById<TextView>(R.id.profile_recipe_title)
        var recipeDescriptionTextView = itemView.findViewById<TextView>(R.id.profile_recipe_description)
        var recipeImage = itemView.findViewById<ImageView>(R.id.recipe_profile_image)


    }

    private var mRecipes = listOf<Recipes>()
    private var mUsers= listOf<User>()
    // create a custom view holder class to supply adapter


    override fun getItemCount() = mRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //create a view

        //LayoutInflater= Instantiates a layout XML file into its corresponding View objects.

        val layoutInflater =  LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.profile_recipes_layout, parent, false)

        return ViewHolder(cellForRow)

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipes = mRecipes[position]
        val users = mUsers[position]


        holder.titleTextView.text = recipes.recipeTitle
        holder.recipeDescriptionTextView.text = recipes.recipeDescription
        Picasso.get().load(recipes.recipeImageString).fit().centerCrop().into(holder.recipeImage)

    }

    fun update(recipes: List<Recipes>){
        mRecipes = recipes
        notifyDataSetChanged()

    }

}