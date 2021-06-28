package com.example.recipestorer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.recipestorer.Adapters.AccountSettingsAdapter
import com.example.recipestorer.Fragments.EditProfileFragment
import com.example.recipestorer.Fragments.SignOutFragment
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.fragment_toolbar.*

class AccountSettings: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        //val accountSettingsImage = findViewById<ImageView>(R.id.account_set_image)

        val backArrow = findViewById<ImageView>(R.id.backicon)


        backArrow.setOnClickListener {
            finish()
            //supportFragmentManager.beginTransaction().replace(R.id.home_frag_container, ProfileFragment())
            //supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ProfileFragment())
        }

        val listView = findViewById<ListView>(R.id.Account_Set_List_View)

        val options = ArrayList<String>()

        options.add(getString(R.string.EditProfileFragment))
        options.add(getString(R.string.SignOutFragment))

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, options)

        listView.adapter = adapter

        val viewPager = findViewById<ViewPager>(R.id.viewPager)


        listView.setOnItemClickListener { adapterView, view, position, l ->

            setViewPager(position)
        }


        setUpFragment()






    }

    fun setUpFragment() {
        val sectionStatePagerAdapter = AccountSettingsAdapter(supportFragmentManager)
        sectionStatePagerAdapter.addFragment(
            EditProfileFragment(),
            getString(R.string.EditProfileFragment)
        )

        sectionStatePagerAdapter.addFragment(
            SignOutFragment(),
            getString(R.string.SignOutFragment)
        )




    }

    fun setViewPager(fragNum: Int){
        val sectionStatePagerAdapter = AccountSettingsAdapter(supportFragmentManager)
        sectionStatePagerAdapter.addFragment(
            EditProfileFragment(),
            getString(R.string.EditProfileFragment)
        )

        sectionStatePagerAdapter.addFragment(
            SignOutFragment(),
            getString(R.string.SignOutFragment)
        )

        relLayout1.visibility = View.GONE
        viewPager.adapter = sectionStatePagerAdapter
        viewPager.currentItem = fragNum




    }


    /*fun navigateToAccountSettings(context: Context?, accountSettingsImage: ImageView){



        accountSettingsImage.setOnClickListener {
            val intent = Intent(context, AccountSettings::class.java)
            startActivity(intent)
        }

    }*/
}