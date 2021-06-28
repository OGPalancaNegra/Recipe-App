package com.example.recipestorer

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val notRegisteredText = findViewById<TextView>(R.id.registered_text)

        notRegisteredText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener {
            loginUser()
        }




    }

    private fun loginUser(){

        val emailTextView = findViewById<EditText>(R.id.email_edit_text_view)
        val passwordTextView = findViewById<EditText>(R.id.password_edit_text_view)

        val email = emailTextView.text.toString()
        val password = passwordTextView.text.toString()

        when {
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
                val progressDialog = ProgressDialog(this@LoginActivity)
                //set the title of the progress dialog "sign up" by implementing the setTitle() function


                progressDialog.setTitle("Login")
                // set "hold up bruda" message to progress bar by implementing the setMessage function
                progressDialog.setMessage("Hold up bruda...")
                // make removing of progress bar by touching on the outside false by using
                progressDialog.setCanceledOnTouchOutside(false)

                //show the progress dialog
                progressDialog.show()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        progressDialog.dismiss()

                        //send user to main application

                        val intent = Intent(this@LoginActivity, BottomNavActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    }

                    else {
                        val message = it.exception!!.toString()
                        Toast.makeText(this, "Error: @message", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }

                }



            }

        }




    }


    // override onStart to automaticaly take user to main page if logged in already

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null)
        {
            val intent = Intent(this@LoginActivity, BottomNavActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


    }


}