package com.example.recipestorer.Models

class User2 {

    private var username: String = ""
    private var name: String = ""
    private var description: String = ""
    private var imageString: String = ""
    private var uid: String = ""
    private var email: String = ""

    constructor()

    constructor(username: String, name: String, description: String, imageString: String,  uid: String, email:String){
        this.username = username
        this.name = name
        this.description = description
        this.imageString= imageString
        this.uid = uid
        this.email = email
    }

    fun getUsername():String{
        return username
    }

    fun setUsername(username: String){
        this.username= username
    }

    fun getName():String{
        return name
    }

    fun setName(name: String){
        this.name= name
    }


    fun getDescription():String{
        return description
    }

    fun setDescription(description: String){
        this.description= description
    }

    fun getImage():String{
        return imageString
    }

    fun setImage(imageString: String){
        this.imageString= imageString
    }

    fun getUid():String{
        return uid
    }

    fun setUid(uid: String){
        this.uid= uid
    }

    fun getEmail():String{
        return email
    }

    fun setEmail(uid: String){
        this.email= email
    }



}