package com.androiddevs.mvvmnewsapp.api


import com.androiddevs.mvvmnewsapp.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PostServise {
    @Headers("app-id: 625452bb18a5849f62819242")
    @GET("post")
    suspend fun getPosts(): Response<PostResponse>



    @Headers("app-id: 625452bb18a5849f62819242")
    @GET("tag/{q}/post")
    suspend fun searchForPosts(
        @Path("q")
        searchQuery :String
    ): Response<PostResponse>



    @Headers("app-id: 625452bb18a5849f62819242")
    @DELETE("post/{post}")
    suspend fun deletePost(
        @Path("post") id: String
    ): Response<IdDelete>


    @Headers("app-id: 625452bb18a5849f62819242")
    @POST("post/create")
    suspend fun createPost(
        @Body createPost:CreatePost

        ): Response<Data>


}