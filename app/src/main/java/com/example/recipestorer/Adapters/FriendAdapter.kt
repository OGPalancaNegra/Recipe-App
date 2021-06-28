package com.example.recipestorer.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.recipestorer.Models.CustomGlideModule
import com.example.recipestorer.Models.User
import com.example.recipestorer.R
import kotlinx.android.synthetic.main.search_users_layout.view.*

@GlideModule
class CustomGlideModule: AppGlideModule() {
}

class FriendAdapter(private val listener: Listener ,private val mContext: Context, private var isFragment: Boolean = false): RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

interface Listener {

    fun follow(uid: String)
    fun unfollow(uid: String)

}


    private var mUsers = listOf<User>()
    private var mFollows = mapOf<String, Boolean>()
    private var mPositions= mapOf<String, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.ViewHolder {
        /*val layoutInflater =  LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_users_layout, parent, false)

        */

        val view = LayoutInflater.from(mContext).inflate(R.layout.search_users_layout, parent, false)

        return FriendAdapter.ViewHolder(view)
    }

    override fun getItemCount()= mUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder){
            val user = mUsers[position]
            view.username_text_view2.text = user.username
            view.name_text_view2.text= user.name
            view.userProfile2.loadImage(user.imageString)

            view.follow_button.setOnClickListener { listener.follow(user.uid) }
            view.unfollow_button.setOnClickListener { listener.unfollow((user.uid)) }

            val follows = mFollows[user.uid] ?: false
            if (follows){
                view.follow_button.visibility = View.GONE
                view.unfollow_button.visibility = View.VISIBLE

            }else {
                view.follow_button.visibility = View.VISIBLE
                view.unfollow_button.visibility = View.GONE
            }
        }

    }


    fun update(users: List<User>, follows: Map<String, Boolean>){
        mUsers = users
        mPositions = users.withIndex().map {(idx, user) -> user.uid!! to idx}.toMap()
        mFollows = follows
        notifyDataSetChanged()

    }

    fun followed(uid: String){

        mFollows += (uid to true)
        notifyItemChanged(mPositions[uid]!!)
    }

    fun unfollowed(uid: String){

        mFollows -= uid
        notifyItemChanged(mPositions[uid]!!)
    }


    fun ImageView.loadImage(image: String){

        Glide.with(this).load(image).into(this)
    }



}