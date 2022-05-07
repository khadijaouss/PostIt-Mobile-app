package com.androiddevs.mvvmnewsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.repository.PostRepository
//class pour supprimer un post
class PostDeleteActivity: AppCompatActivity() {
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_post)
        incomingIntent
    }
    private val incomingIntent: Unit
        private get() {
            if (intent.hasExtra("id")) {
                val id = intent.getStringExtra("id") //id du post ou on a fait un LongClick
                val warning: TextView = findViewById<View>(R.id.text) as TextView
                val message = "Post  with $id is deleted "
                //message s'affiche pour confirmer la supression du post
                warning.setText(message)
                if (id != null) {
                    delete(id)
                }
            }
        }


   //OnLongClickListener la fonction delete(id) est appelée
    private fun delete(id: String) {
        val repository = PostRepository()
        val viewModelFactory = MainViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel(repository)::class.java)
        viewModel.deletePost(id)
    }
}