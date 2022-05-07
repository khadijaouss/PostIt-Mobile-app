package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.MainRecyclerAdapter
import com.androiddevs.mvvmnewsapp.ui.MainActivity
import com.androiddevs.mvvmnewsapp.ui.MainViewModel
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment(R.layout.search_fragment) {
   val TAG="searchFragment"
    lateinit var viewModel: MainViewModel
    lateinit var postsAdapter:MainRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
       setupRecyclerView()

        postsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply{
                putSerializable("post",it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_postFragment,
                bundle
            )
        }
        var job: Job?=null
        etSearch.addTextChangedListener { editable->
            job?.cancel()
            job= MainScope().launch{
            delay(500L)
                editable?.let{
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchPosts(editable.toString())
                    }
                }
            }

        }
        viewModel.searchPosts.observe(viewLifecycleOwner, Observer {   response->
            when(response){
                is Resource.Success->{
                    response.data?.let{postResponse->
                        postsAdapter.differ.submitList(postResponse.data)

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
    private fun setupRecyclerView() {
        postsAdapter = MainRecyclerAdapter()
        rvSearchPosts.apply {
            adapter = postsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}