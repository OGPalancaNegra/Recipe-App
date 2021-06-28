package com.example.recipestorer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activty_create_recipe.*
import kotlinx.android.synthetic.main.activty_create_recipe.recipe_description
import kotlinx.android.synthetic.main.activty_create_recipe.recipe_ingredients
import kotlinx.android.synthetic.main.activty_create_recipe.recipe_instructions
import kotlinx.android.synthetic.main.activty_create_recipe.recipe_title

class CreateRecipe: AppCompatActivity() {

    private lateinit var mUser: User
    private lateinit var recipeImageString: String

    private var recipeImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_create_recipe)


        create_recipe_back_icon.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
        }


        val recipeImageButton = findViewById<ImageButton>(R.id.RecipeImageButton)

        recipeImageButton.setOnClickListener {
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type ="image/*"
            startActivityForResult(imagePickerIntent, 0)

        }




        //perform create recipe function after user clicks on Save Recipe Icon

        saveRecipe.setOnClickListener {
            createRecipe()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0){
            //if user selected do else notify user that image was not with Activity.result_ok
            if (resultCode== Activity.RESULT_OK){



                if (data != null) {
                    recipeImageUri = data.data
                }


                //RecipeImageButton.setImageURI(recipeImageUri)
                Picasso.get().load(recipeImageUri).centerCrop().fit().into(RecipeImageButton)


                }

            } else {
                Toast.makeText(this,"IMAGE NOT SELECTED" , Toast.LENGTH_LONG)
            }


        }



    fun createRecipe(){




        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val recipeImageRef = FirebaseStorage.getInstance().getReference("/user/$uid/images").child(
            recipeImageUri?.lastPathSegment.toString()
        )

        recipeImageUri?.let { recipeImageRef.putFile(it) }?.addOnCompleteListener { it ->
            if (it.isSuccessful){

                val uid = FirebaseAuth.getInstance().currentUser!!.uid

                recipeImageRef.downloadUrl.addOnSuccessListener {it ->

                    recipeImageString = it.toString()

                    //create image database reference to add images to its own dat...nah

                    val databaseRefForImage = FirebaseDatabase.getInstance().getReference("recipeImages/$uid/imageString").push()

                    val userDatabaseRef = FirebaseDatabase.getInstance().getReference("recipes/$uid")

                    //create condition to


                    userDatabaseRef.push().setValue(Recipes(
                        uid = uid,
                        recipeImageString = recipeImageString,
                        recipeTitle = recipe_title.text.toString(),
                        recipeInstructions = recipe_instructions.text.toString(),
                        recipeIngredients = recipe_ingredients.text.toString(),
                        recipeDescription = recipe_description.text.toString(),
                        //numOfIngredients = Integer.parseInt(recipe_number_of_ingredients.text.toString()),
                        numOfIngredients = recipe_number_of_ingredients.text.toString(),
                        recipeServingSize = recipe_serving_size.text.toString(),
                        recipeStepsCount = Integer.parseInt(recipe_step_count.text.toString())
                        //recipeCalories = recipe_calories.text.toString().toLong()



                    ))
                        .addOnCompleteListener {
                            if (it.isSuccessful){
                                val intent = Intent(this, BottomNavActivity::class.java)
                                startActivity(intent)
                                finish()
                                //take to user profile?



                            } else {
                                Toast.makeText(this,"IMAGE NOT PUT IN FILE" , Toast.LENGTH_LONG)


                            }




                        }

                }

                // Picasso.get().load(User().imageString).error(R.drawable.default_profile_image).centerCrop().fit().into(profilePhoto)

            }


        }


    }


    }



