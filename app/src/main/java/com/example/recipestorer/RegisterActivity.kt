package com.example.recipestorer

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recipestorer.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registeredText = findViewById<TextView>(R.id.registered_text)

        registeredText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        registerButton.setOnClickListener {

            regsiterUser()

        }



    }

    private fun regsiterUser() {
        val username = username_text_view.text.toString().toLowerCase()
        val name = name_text_view.text.toString().toLowerCase()
        val email = email_text_view.text.toString()
        val password = password_text_view.text.toString()
        //val imageString = ""


        when {

            TextUtils.isEmpty(username) -> Toast.makeText(
                this,
                "Username not entered",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(name) -> Toast.makeText(
                this,
                "name not entered",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(email) -> Toast.makeText(
                this,
                "Email not entered",
                Toast.LENGTH_LONG
            ).show()
            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "Password not entered",
                Toast.LENGTH_LONG
            ).show()

            else -> {

                //create a progress diaglog
                val progressDialog = ProgressDialog(this@RegisterActivity)
                //set the title of the progress dialog "sign up" by implementing the setTitle() function


                progressDialog.setTitle("Register")
                // set "hold up bruda" message to progress bar by implementing the setMessage function
                progressDialog.setMessage("Hold up bruda...")
                // make removing of progress bar by touching on the outside false by using
                progressDialog.setCanceledOnTouchOutside(false)

                //show the progress dialog
                progressDialog.show()

                //create instance of firebase auth to enable us to create user with Email and Password

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {


                            saveUserInfo(name, username, email, progressDialog)


                        } else {

                            // make a toast with the it/task complettion exception
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: @message", Toast.LENGTH_LONG).show()
                            //   Log.d(TAG, "Authentication Failed. User info not saved")
                            //in case of error dismiss progress dialog
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()


                        }


                    }


            }

        }




    }

    /*private fun uploadImageToStorage() {

        val fileName = UUID.randomUUID().toString()
        val imageFileRef = FirebaseStorage.getInstance().getReference("profileImage/$fileName")

        val defaultProfileImage = "https://www.google.co.uk/search?q=profile+image&sxsrf=ALeKk03nLHZI7XlVE9VHLVqPI-MOhE0twA:1616152879129&tbm=isch&source=iu&ictx=1&fir=ZbfgeaptF8Y5ZM%252CSmb2EEjVhvpzWM%252C_&vet=1&usg=AI4_-kRNMYUjKwXy7gy-PotZoWQZA2eGOQ&sa=X&ved=2ahUKEwjv94zSnrzvAhXqaRUIHbJNBKAQ9QF6BAgEEAE&biw=1707&bih=785&dpr=1.13#imgrc=ZbfgeaptF8Y5ZM"

        imageFileRef.








    }*/

    private fun saveUserInfo(name: String, username:String, email: String, progressDialog: ProgressDialog) {

        // ?: added to add string incase of emptiness
        val uid = FirebaseAuth.getInstance().uid ?: ""
        //val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val databaseRef = FirebaseDatabase.getInstance().getReference("users/$uid")

        val user = User(uid, name, username, email)

        databaseRef.setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful){

                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Account Created",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this@RegisterActivity, BottomNavActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            else {


                val message = task.exception!!.toString()
                Toast.makeText(this, "Error: @message", Toast.LENGTH_LONG).show()
                //   Log.d(TAG, "Authentication Failed. User info not saved")
                //in case of error dismiss progress dialog
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()


            }
        }





    }
}

