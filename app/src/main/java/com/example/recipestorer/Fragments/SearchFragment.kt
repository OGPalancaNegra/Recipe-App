package com.example.recipestorer.Fragments

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipestorer.Adapters.FriendAdapter
import com.example.recipestorer.Adapters.RecipeAdapter
import com.example.recipestorer.Models.Recipes
import com.example.recipestorer.Models.User
import com.example.recipestorer.Models.User2
import com.example.recipestorer.R
import com.example.recipestorer.Utils.ValueEventListenerAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment(), FriendAdapter.Listener {
    private lateinit var mUser: User
    private lateinit var mUsers: List<User>
    private lateinit var mFriendAdapter: FriendAdapter
    private lateinit var mPRecipe: List<Recipes>
    private var mRecipe: MutableList<Recipes>? = null
    private var recipeAdapter: RecipeAdapter? = null
    var recipeList: ArrayList<Recipes>? = null
    private var recipeImageUri: Uri? = null

    private var recyclerView: RecyclerView? = null
    private var mUser2: MutableList<User>? = null
    private var userAdapter: FriendAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)


        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        mFriendAdapter = context?.let { FriendAdapter(this ,it,true) }!!

        view.search_recycler_view_2.adapter = mFriendAdapter
        view.search_recycler_view_2.layoutManager = LinearLayoutManager(context)


        val usersRef = FirebaseDatabase.getInstance().getReference("users").addValueEventListener(ValueEventListenerAdapter{
            val allUsers= it.children.map { it.getValue(User::class.java)!!.copy(uid = it.key!!) }
            val (userList, otherUsersList) = allUsers.partition { it.uid == uid }
            mUser = userList.first()
            mUsers = otherUsersList

            mFriendAdapter.update(mUsers, mUser.follows)

        }
        )






        return view

    }

    override fun follow(uid: String) {
        setFollow (uid, true) {
            mFriendAdapter.followed(uid)
        }


    }

    override fun unfollow(uid: String) {
        setFollow(uid, false){
            mFriendAdapter.unfollowed(uid)
        }
    }

    private fun setFollow(uid: String, follow: Boolean, onSuccess: ()-> Unit) {
        val mUserUID = mUser.uid

        fun DatabaseReference.setValueTrueOrRemove(value: Boolean) =
            if (value) setValue(true) else removeValue()


        val followTask = FirebaseDatabase.getInstance().getReference("users/$mUserUID/follows/$uid")
        val setFollow = if (follow) followTask.setValue(true) else followTask.removeValue()

        val followerTask =
            FirebaseDatabase.getInstance().getReference("users/$uid/followers/$mUserUID")

        val setFollower = if (follow) followerTask.setValue(true) else followerTask.removeValue()


        fun <T> task(block: (TaskCompletionSource<T>) -> Unit): Task<T> {
            val taskSource = TaskCompletionSource<T>()
            block(taskSource)
            return taskSource.task
        }

        //code to add user followed recipes to user recipes to display on home or profile later!!!

        val recipeFeedTasks= task<Void> {taskSource ->

            FirebaseDatabase.getInstance().getReference("recipes/$uid")
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val recipesMap = if (follow) {
                        it.children.map { it.key to it.value }.toMap()

                    } else {
                        it.children.map { it.key to null }.toMap()
                    }
                    FirebaseDatabase.getInstance().getReference("recipes/$mUserUID")
                        .updateChildren(recipesMap)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                taskSource.setResult(it.result)
                            } else {
                                taskSource.setException(it.exception!!)

                            }
                        }

                })
        }
            Tasks.whenAll(recipeFeedTasks)
            setFollow.continueWithTask({ setFollower }).addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    Toast.makeText(context, "error", Toast.LENGTH_LONG)

                }
            }

        }






}


class TaskSourceOnCompleteListener<T>(private val taskSource: TaskCompletionSource<T>):
        OnCompleteListener<T> {
    override fun onComplete(task: Task<T>) {
        if (task.isSuccessful) {
            taskSource.setResult(task.result)
        } else {
            taskSource.setException(task.exception!!)
        }
    }
}

