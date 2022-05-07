package com.androiddevs.mvvmnewsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.MainRecyclerAdapter
import com.androiddevs.mvvmnewsapp.ui.MainActivity
import com.androiddevs.mvvmnewsapp.ui.MainViewModel
import com.androiddevs.mvvmnewsapp.ui.PostCreateActivity
import com.androiddevs.mvvmnewsapp.util.Resource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.post_grid_item.*


class MainFragment : Fragment(R.layout.main_fragment)  {
    lateinit var addPost:FloatingActionButton
    lateinit var viewModel: MainViewModel
    lateinit var postsAdapter: MainRecyclerAdapter
    val TAG = "MainFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        addPost = getView()!!.findViewById<View>(R.id.addPost) as FloatingActionButton
        addPost.setOnClickListener {
          val intent=Intent(this@MainFragment.requireContext(),PostCreateActivity::class.java)
          startActivity(intent)
        }

        postsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply{
                putSerializable("post",it)
            }
            findNavController().navigate(
                 R.id.action_mainFragment_to_postFragment,
                bundle
            )
        }

        
        viewModel.posts.observe(viewLifecycleOwner, Observer {   response->
            when(response){
                is Resource.Success->{
                    response.data?.let{postResponse->
                        postsAdapter.differ.submitList(postResponse.data.toList())
                       if(isLastPage){
                           rvPosts.setPadding(0,0,0,0)
                       }
                    }
                }
                is Resource.Error->{
                    response.message?.let{message->
                        Log.e(TAG,"error:$message")
                    }
                }
                is Resource.Loading->{
                }
            }

        })

    }
   var isLoading=false
    var isLastPage=false
    var isScrolling=false
    val scrollListener=object: RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager=recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition=layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount=layoutManager.childCount
            val totalItemCount=layoutManager.itemCount
            val isNotLoadingAndNotLastPage=!isLoading && !isLastPage
            val isAtLastItem=firstVisibleItemPosition +visibleItemCount >= totalItemCount
            val isNotAtBeginning=firstVisibleItemPosition>=0
            val isTotalMoreThanVisible=totalItemCount>=20
            val shouldPaginate=isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
           if(shouldPaginate){
               viewModel.getPosts()
               isScrolling=false
           }
        }
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }
    }
    private fun setupRecyclerView() {
        postsAdapter = MainRecyclerAdapter()
        rvPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@MainFragment.scrollListener)
        }

    }


}