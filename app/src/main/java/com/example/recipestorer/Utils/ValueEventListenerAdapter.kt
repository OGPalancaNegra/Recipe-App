package com.example.recipestorer.Utils

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ValueEventListenerAdapter(val handler: (DataSnapshot) -> Unit): ValueEventListener {
    override fun onCancelled(error: DatabaseError) {
        Log.e(TAG, "onCancelled", error.toException())
    }

    override fun onDataChange(snapshot: DataSnapshot) {
        handler(snapshot)
    }


}