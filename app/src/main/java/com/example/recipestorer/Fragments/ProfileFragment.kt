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
import com.example.recipestorer.Adapters.PersonalRecipeAdapter
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import com.example.recipestorer.Utils.ValueEventListenerAdapter
import com.example.recipestorer.DisplayRecipes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activty_create_recipe.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_toolbar.view.*


class ProfileFragment : Fragment(), PersonalRecipeAdapter.OnItemClickListener {

    //private val context = getContext()



    private lateinit var mUser: User
    private lateinit var users: List<User>
    private var mRecipes: MutableList<Recipes>? = null
    //private var recipeImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)



        //context?.let { AccountSettings().navigateToAccountSettings(it, view.account_set_image ) }

        view.account_set_image.setOnClickListener {
            val intent = Intent(context, AccountSettings::class.java)
            startActivity(intent)
        }

        //Picasso.get().load(R.drawable.default_profile_image).fit().centerCrop().into(circleImageView)

        //val ingredientEditText = view.ingredient

        val constraintLayout = view.constraintLayout

        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("users/$uid/follows").addListenerForSingleValueEvent(ValueEventListenerAdapter{
            val followsCount = it.childrenCount
            view.user_following_count.text = followsCount.toString()

        })

        FirebaseDatabase.getInstance().getReference("users/$uid/followers").addListenerForSingleValueEvent(ValueEventListenerAdapter{
            val followersCount = it.childrenCount
            view.user_follower_count.text = followersCount.toString()

        })

        //set follower , recipe and followers count


        // set user profile image on image view
        //get a list of the recipe uid strings




        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$uid")

        databaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
            mUser = it.getValue(User::class.java)!!
            view.username_text.setText(mUser.username, TextView.BufferType.EDITABLE)
            if (mUser.imageString?.isEmpty()!!) {
                circleImageView.setImageResource(R.drawable.default_profile_image);
            } else{
                Picasso.get().load(mUser.imageString).into(view.circleImageView)
            }

        })


        //val recyclerView = view.findViewById<RecyclerView>(R.id.profile_recycler_view)


        FirebaseDatabase.getInstance().getReference("recipes/$uid").addValueEventListener(
            ValueEventListenerAdapter{
                val recipes = it.children.map { it.getValue(Recipes::class.java)!! }

                val (personalRecipe, otherUserRecipes) = recipes.partition { it.uid == uid }


                //get users

                databaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    mUser = it.getValue(User::class.java)!!
                    val recipesCount = personalRecipe.count()
                    view.user_recipe_count.text = recipesCount.toString()

                    view.username_text.setText(mUser.username, TextView.BufferType.EDITABLE)
                    view.profile_recycler_view.layoutManager= LinearLayoutManager(context)
                    view.profile_recycler_view.setHasFixedSize(true)
                    view.profile_recycler_view.adapter =
                        context?.let { it1 -> PersonalRecipeAdapter(it1, personalRecipe, mUser, this) }


                })



            })


        return view
    }


    override fun onItemClick(position: Int, recipes: List<Recipes>) {
        Toast.makeText(context, recipes[position].recipeTitle, Toast.LENGTH_SHORT).show()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid


        users = listOf<User>()

        val intent = Intent(context, DisplayRecipes::class.java)
        intent.putExtra("recipe_title", recipes[position].recipeTitle)
        intent.putExtra("recipe_ingredients", recipes[position].recipeIngredients)
        intent.putExtra("recipe_instructions", recipes[position].recipeInstructions)
        intent.putExtra("recipe_description", recipes[position].recipeDescription)
        intent.putExtra("recipe_image", recipes[position].recipeImageString)
        intent.putExtra("recipe_ingredient_count", recipes[position].numOfIngredients)
        intent.putExtra("recipe_steps_count", recipes[position].recipeStepsCount.toString())
        intent.putExtra("recipe_serving_size", recipes[position].recipeServingSize)

        startActivity(intent)


        // set user profile image on image view
        //get a list of the recipe uid strings


        //val clickedItem = personalRecipe[position]




    }










    //private val textWatcher = object : TextWatcher{

}





