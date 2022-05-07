package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.MainRecyclerAdapter
import com.androiddevs.mvvmnewsapp.ui.MainActivity
import com.androiddevs.mvvmnewsapp.ui.MainViewModel
import com.androiddevs.mvvmnewsapp.util.Resource
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.post_fragment.*
import kotlinx.android.synthetic.main.post_grid_item.view.*

class PostFragment : Fragment(R.layout.post_fragment) {

    lateinit var viewModel: MainViewModel
    val args: PostFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val post=args.post
        Glide.with(this).load(post.image).into(image)
        Glide.with(this).load(post.owner?.picture).circleCrop().into(picture)
        postText.text=post.text
        tag1.text=post.tags?.get(0)
        tag2.text=post.tags?.get(1)
        tag3.text=post.tags?.get(2)
        publishDate.text=post.publishDate
        firstname.text=post.owner?.firstName
        lastname.text=post.owner?.lastName
        title.text=post.owner?.title
        likes.text=post.likes.toString()


    }

}