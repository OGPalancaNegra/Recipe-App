package com.example.recipestorer.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.recipestorer.BottomNavActivity
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import com.example.recipestorer.Utils.ValueEventListenerAdapter
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import java.text.SimpleDateFormat
import java.util.*


class EditProfileFragment : Fragment() {
    
    private lateinit var mUser: User
    private lateinit var imageString: String

    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        //view.profilePhoto
        Picasso.get().load(R.drawable.default_profile_image).fit().centerCrop().into(view.profilePhoto)



        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$uid")

        databaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
            mUser = it.getValue(User::class.java)!!
            edit_email_text_view.setText(mUser.email, TextView.BufferType.EDITABLE)
            edit_name_text_view.setText(mUser.name, TextView.BufferType.EDITABLE)
            edit_username_text_view.setText(mUser.username, TextView.BufferType.EDITABLE)
            //activity?.let { it1 -> Glide.with(it1).load(mUser.imageUrl).into(profilePhoto) }
            //Picasso.get().load(mUser.imageUrl).fit().centerCrop().into(profilePhoto)
            //activity?.let { it1 -> GlideApp.with(it1).load(mUser.imageUrl).into(profilePhoto) }
            //activity?.let { it1 -> Glide.with(it1).load(mUser.imageUrl).fitCenter().into(view.profilePhoto) }
            //edit_profile_password.setText(mUser.pass, TextView.BufferType.EDITABLE)
            //Picasso.get().load(mUser?.imageString).placeholder(R.drawable.default_profile_image).error(R.drawable.default_profile_image).fit().centerCrop().into(profilePhoto)
            //GlideApp.with(context!!).load(mUser.imageString).into(profilePhoto)
            //context?.let { it1 -> GlideApp.with(it1).load(mUser.imageUrl).into(profilePhoto) }
            if (mUser.imageString?.isEmpty()!!) {
                profilePhoto.setImageResource(R.drawable.default_profile_image);
            } else{
                Picasso.get().load(mUser.imageString).into(profilePhoto);
            }

        })


        view.edit_profile_save_image.setOnClickListener {
            //uploadImageToFirebase()
            updateProfile()

        }

        view.edit_profile_backicon.setOnClickListener {
            val intent = Intent(activity, BottomNavActivity::class.java)
            startActivity(intent)
        }



        view.changeProfilePhoto.setOnClickListener {
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type ="image/*"
            startActivityForResult(imagePickerIntent, 0)

        }




        return view

    }

    /*private fun uploadImageToFirebase() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        ref.putFile(imageUri!!).addOnCompleteListener {
            Log.d("UploadImage", "Sucessfully Uploaded Image")



            ref.downloadUrl.addOnSuccessListener {
                //imageString = it.toString()
                FirebaseDatabase.getInstance().reference.child("users/$uid/photo").setValue(it.toString())
            }

            //val user = User(uid,name,username,email,imageUrl)

            if (it.isSuccessful){
                val imageString = it.toString()
                //mUser = mUser.copy(imageUrl = imageString)
                FirebaseDatabase.getInstance().reference.child("users/$uid/photo").setValue(imageString).addOnCompleteListener {
                    if (it.isSuccessful) {
                        mUser = mUser.copy(imageUrl = imageString)

                    } else {
                        Toast.makeText(activity, "Not in Database", Toast.LENGTH_SHORT)
                    }



                }
            } else {
                Toast.makeText(activity, "Not in Database", Toast.LENGTH_SHORT)

            }



        }


    }*/

    private fun createImageFile(){
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //check if request code the same


        if (requestCode == 0){
            //if user selected do else notify user that image was not with Activity.result_ok
            if (resultCode== Activity.RESULT_OK){

                //get acess to image with the data intent thats passed in as paramter

                //get location of photo
                //photoUri = data?.data
                //view?.profilePhoto?.setImageURI(photoUri)

                //Picasso.get().load(photoUri).fit().centerCrop().into(view?.profilePhoto)


                //val filename = UUID.randomUUID().toString()
                val uid = FirebaseAuth.getInstance().currentUser!!.uid

                val imageRef = FirebaseStorage.getInstance().getReference("/user/$uid/images")

                imageUri = data?.data
                view?.profilePhoto?.setImageURI(imageUri)





                imageUri?.let { imageRef.putFile(it) }?.addOnCompleteListener { it ->
                    if (it.isSuccessful){

                        imageRef.downloadUrl.addOnSuccessListener {it ->

                            val imageString2 = it.toString()

                            val databaseRefForImage = FirebaseDatabase.getInstance().getReference("users/$uid/imageString")

                            val databaseRef = FirebaseDatabase.getInstance().getReference("/users/$uid")


                            //mUser = mUser.copy(imageString = it.toString())

                            //val user = User(uid, edit_name_text_view.text.toString(), edit_username_text_view.text.toString(), edit_email_text_view.text.toString(),imageString2)

                            //User().imageString= imageString2

                            databaseRefForImage.setValue(imageString2)

                           // Picasso.get().load(User().imageString).error(R.drawable.default_profile_image).centerCrop().fit().into(profilePhoto)

                        }


                    } else {
                        Toast.makeText(activity,"IMAGE NOT PUT IN FILE" , Toast.LENGTH_LONG)

                    }

                }






                //FirebaseStorage.getInstance().reference.child("users/$uid/photo").pu



            } else {
                Toast.makeText(activity,"IMAGE NOT SELECTED" , Toast.LENGTH_LONG)
            }


        }
    }



    private fun updateProfile() {

        val user= User(name = edit_name_text_view.text.toString(), username = edit_username_text_view.text.toString(),email = edit_email_text_view.text.toString())

        // validate user * create toasts to deal with empy input field

        val error = correctUser(user)

        if (error == null) {
            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            val databaseReference = FirebaseDatabase.getInstance().getReference("users/$uid")
            databaseReference.addListenerForSingleValueEvent(ValueEventListenerAdapter {
                val mUser = it.getValue(User::class.java)})

            if (user.email == mUser.email) {
                updateUser(user)


            } else {
                //reauthenticate and update email in auth
                val password = view?.edit_profile_password?.text.toString()
                val email = view?.edit_email_text_view?.text.toString()
                val credential = EmailAuthProvider.getCredential(email, password)
                FirebaseAuth.getInstance().currentUser!!.reauthenticate(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        FirebaseAuth.getInstance().currentUser!!.updateEmail(mUser.email).addOnCompleteListener {
                            if (it.isSuccessful){
                                updateUser(user)
                            } else {
                                val message = it.exception!!.toString()
                                Toast.makeText(
                                    activity,
                                    "Error: @message",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
                }
            }

        }




        //validate(user)

    }

    private fun updateUser(user: User) {
        val updateMap = mutableMapOf<String, Any>()
        if (user.name != mUser.name) updateMap["name"] = user.name
        if (user.username != mUser.username) updateMap["username"] = user.username
        if (user.email != mUser.email) updateMap["email"] = user.email


        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        FirebaseDatabase.getInstance().getReference("users/$uid").updateChildren(updateMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        activity,
                        "Profile Saved",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(activity, BottomNavActivity::class.java)
                    startActivity(intent)




                } else {
                    val message = it.exception!!.toString()
                    Toast.makeText(
                        activity,
                        "Error: @message",
                        Toast.LENGTH_LONG
                    ).show()
                }


            }
    }

    fun correctUser(user: User): String? =
        when {

            user.username.isEmpty() -> "Please Enter Username"
            user.name.isEmpty() -> "Please Enter Name"
            user.email.isEmpty() -> "Please Enter Email"


            else -> null

        }

    }



