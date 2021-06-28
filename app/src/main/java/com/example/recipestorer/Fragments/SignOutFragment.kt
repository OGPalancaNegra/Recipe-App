package com.example.recipestorer.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipestorer.LoginActivity
import com.example.recipestorer.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_out.*
import kotlinx.android.synthetic.main.fragment_sign_out.view.*

class SignOutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_out, container, false)

        view.signoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            //send user to login activity

            val intent = Intent(context, LoginActivity::class.java)
            //clear the
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

        }

        return view
    }


}