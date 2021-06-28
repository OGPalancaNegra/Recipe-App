package com.example.recipestorer.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipestorer.AccountSettings

import com.example.recipestorer.Adapters.HomeRecipesAdapter
import com.example.recipestorer.Adapters.PersonalRecipeAdapter
import com.example.recipestorer.DisplayRecipes
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import com.example.recipestorer.Utils.ValueEventListenerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activty_view_recipes.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_toolbar.view.*


class HomeFragment : Fragment(), HomeRecipesAdapter.OnItemClickListener {


    private lateinit var mUsers: List<User>
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)


        //set username to hometoolbar

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val usernameDatabaseReference = FirebaseDatabase.getInstance().getReference("users/$uid")

        usernameDatabaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
            mUser = it.getValue(User::class.java)!!
            homeView.username_text?.setText(mUser.username, TextView.BufferType.EDITABLE)

        })

        homeView.account_set_image.setOnClickListener {
            val intent = Intent(context, AccountSettings::class.java)
            startActivity(intent)
        }






        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        FirebaseDatabase.getInstance().getReference("recipes/$uid").addValueEventListener(
            ValueEventListenerAdapter{
                val recipes = it.children.map { it.getValue(Recipes::class.java)!! }

                //val (personalRecipe, otherUserRecipes) = recipes.partition { it.uid == uid }

                databaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val mUser =  it.getValue(User::class.java)!!
                    mUsers = it.children.map {it.getValue(User::class.java)!!}
                    homeView.username_text?.setText(mUser.username, TextView.BufferType.EDITABLE)
                  //  homeView.recipe_chef.text = mUsers[]
                    homeView.home_frag_recycler.layoutManager = LinearLayoutManager(context)
                    homeView.home_frag_recycler.setHasFixedSize(true)
                    homeView.home_frag_recycler.adapter =
                        context?.let { it1 -> HomeRecipesAdapter(it1, recipes, mUsers, this) }


                })

            })


        return homeView
    }

    override fun onItemClick(position: Int, recipes: List<Recipes>, mUser: List<User>) {
        Toast.makeText(context, recipes[position].recipeTitle, Toast.LENGTH_SHORT).show()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid





        val intent = Intent(context, DisplayRecipes::class.java)
        intent.putExtra("recipe_title", recipes[position].recipeTitle)
        intent.putExtra("recipe_ingredients", recipes[position].recipeIngredients)
        intent.putExtra("recipe_instructions", recipes[position].recipeInstructions)
        intent.putExtra("recipe_description", recipes[position].recipeDescription)
        intent.putExtra("recipe_image", recipes[position].recipeImageString)
        intent.putExtra("recipe_ingredient_count", recipes[position].numOfIngredients)
        intent.putExtra("recipe_steps_count", recipes[position].recipeStepsCount.toString())
        intent.putExtra("recipe_serving_size", recipes[position].recipeServingSize)
        //intent.putExtra("recipe_serving_size", recipes[position].)


       // intent.putExtra("recipe_creator", mUser[position].username)

        startActivity(intent)


        // set user profile image on image view
        //get a list of the recipe uid strings


        //val clickedItem = personalRecipe[position]




    }


}