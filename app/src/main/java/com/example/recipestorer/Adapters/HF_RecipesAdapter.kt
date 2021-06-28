package com.example.recipestorer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import com.squareup.picasso.Picasso

class HomeRecipesAdapter(val context: Context, val mRecipes:List<Recipes>, val mUsers: List<User>, private val listener: OnItemClickListener): RecyclerView.Adapter<HomeRecipesAdapter.CustomViewHolder>() {



    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val titleTextView = view.findViewById<TextView>(R.id.hf_recipe_title)
        val descriptionTextView = view.findViewById<TextView>(R.id.hf_recipe_description)
        val recipeChef = view.findViewById<TextView>(R.id.hf_recipe_author)
        val recipeImage = view.findViewById<ImageView>(R.id.hf_recipe_image)

        init {
            view.setOnClickListener(this)

        }

        fun bind (recipes: Recipes, context: Context) {

            titleTextView?.text = recipes.recipeTitle
            descriptionTextView?.text = recipes.recipeDescription
            Picasso.get().load(recipes.recipeImageString).placeholder(R.drawable.ic_round_android_24).fit().centerCrop().into(recipeImage)
            recipeChef?.text = "Ingredient Count:"+ recipes.numOfIngredients

        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            // recyclerView.No_Poistion is a constant of -1 ...this line assures position is not invalid
            if (position != RecyclerView.NO_POSITION) {
                //val recipes: Recipes
                listener.onItemClick(position, mRecipes, mUsers)
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, recipes: List<Recipes>, user: List<User>){

        }
    }

    override fun getItemCount() = mRecipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //create a view

        //LayoutInflater= Instantiates a layout XML file into its corresponding View objects.

        val layoutInflater =  LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_recipes_layout, parent, false)

        return CustomViewHolder(cellForRow)

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        holder?.bind(mRecipes[position], context)

    }




}