package com.androiddevs.mvvmnewsapp.ui

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.*
import com.androiddevs.mvvmnewsapp.repository.PostRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MainViewModel(
    val postRepository: PostRepository
)  : ViewModel(){
    val posts: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val searchPosts: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val createdPost: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    val deletePost: MutableLiveData<Response<IdDelete>> = MutableLiveData()
    var postResponse:PostResponse?=null

    init {
        getPosts()
    }

    fun getPosts() = viewModelScope.launch {
        posts.postValue(Resource.Loading())
        val response = postRepository.getPosts()
        posts.postValue((handlePostsResponse(response)))
    }
    fun searchPosts(searchQuery:String) = viewModelScope.launch {
        searchPosts.postValue(Resource.Loading())
        val response = postRepository.searchPosts(searchQuery)
        searchPosts.postValue((handleSearchPostsResponse(response)))
    }

    fun deletePost(id: String) = viewModelScope.launch {
            val response = postRepository.deletePost(id)
            deletePost.value=response
    }


    fun createPost(createPost:CreatePost) {
        viewModelScope.launch {
            createdPost.postValue(Resource.Loading())
            val response= postRepository.createPost(createPost)
            createdPost.postValue((handleCreatePostsResponse(response)))
        }
    }

    private fun handleCreatePostsResponse(response: Response<Data>): Resource<PostResponse>? {
        if(response.isSuccessful) {
            response.body()?.let {
            }
        }
        return Resource.Error(response.message())
    }


    private fun handlePostsResponse(response: Response<PostResponse>): Resource<PostResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse->
                if(postResponse==null){
                    postResponse=resultResponse
                }else{
                    val oldPosts=postResponse?.data
                    val newPosts=resultResponse.data
                    oldPosts?.addAll(newPosts)
                }
                return Resource.Success(postResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchPostsResponse(response: Response<PostResponse>): Resource<PostResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}