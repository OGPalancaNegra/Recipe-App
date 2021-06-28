package com.example.recipestorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.recipestorer.Fragments.HomeFragment
import com.example.recipestorer.Fragments.ProfileFragment
import com.example.recipestorer.Fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class BottomNavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)





        //listen to user click/input on bottom navigation menu with setOnNavigation


        //navigate to either home , search or profile activty based on which user item user clicked

        bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId){

                R.id.home_nav -> {
                    //set homeFragment
                    setCurrentFragment(homeFragment)
                    }

                R.id.search_nav -> {
                    //set homeFragment
                    setCurrentFragment(searchFragment)
                }

                R.id.profile_nav -> {
                    //set homeFragment
                    setCurrentFragment(profileFragment)
                }


            }

            true
        }


        // set an OnClick Listner
        F_A_B.setOnClickListener {
            val intent = Intent(this, CreateRecipe::class.java)
            startActivity(intent)
        }



    }


    // create function to set current fragment

    //what value changes? fragment so thats parameter
    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment).commit()
        }
    }

}