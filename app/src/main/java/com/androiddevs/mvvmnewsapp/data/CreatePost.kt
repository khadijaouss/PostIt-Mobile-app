package com.androiddevs.mvvmnewsapp.data

data class CreatePost (
    var image:String,
    var firstName: String,
    var lastName: String,
    var title:String,
    var text: String,
    var picture:String,
    var tag1:String,
    var tag2:String,
    var tag3:String,
    var likes:Int=0

)