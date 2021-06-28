package com.example.recipestorer.Models

import com.google.firebase.database.Exclude

data class User(
    val name: String= "",
    val username:String = "",
    val email: String= "",
    val imageString: String="",
    @Exclude val uid: String = "",
    val follows: Map<String, Boolean> = emptyMap(),
    val followers: Map<String, Boolean> = emptyMap())