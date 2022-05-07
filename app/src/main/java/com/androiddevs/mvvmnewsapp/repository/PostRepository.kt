package com.androiddevs.mvvmnewsapp.repository


import com.androiddevs.mvvmnewsapp.data.CreatePost
import com.androiddevs.mvvmnewsapp.data.Data
import com.androiddevs.mvvmnewsapp.data.IdDelete
import com.androiddevs.mvvmnewsapp.data.Owner
import com.androiddevs.mvvmnewsapp.ui.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PostRepository() {
    suspend fun getPosts() =
        RetrofitInstance.api.getPosts()
   suspend fun searchPosts(searchQuery:String)=
       RetrofitInstance.api.searchForPosts(searchQuery)
    suspend fun deletePost(id: String): Response<IdDelete> {
        return RetrofitInstance.api.deletePost(id)
    }
    suspend fun createPost(createPost:CreatePost): Response<Data> {
        return RetrofitInstance.api.createPost(createPost)
    }

}