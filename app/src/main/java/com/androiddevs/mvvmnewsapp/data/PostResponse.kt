package com.androiddevs.mvvmnewsapp.data

data class PostResponse(
    val `data`: MutableList<Data>,
    val limit: Int,
    val page: Int,
    val total: Int
)