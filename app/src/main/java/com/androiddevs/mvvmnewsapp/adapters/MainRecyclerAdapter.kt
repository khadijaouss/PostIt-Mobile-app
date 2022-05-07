package com.androiddevs.mvvmnewsapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.data.Data
import com.androiddevs.mvvmnewsapp.ui.MainActivity
import com.androiddevs.mvvmnewsapp.ui.PostDeleteActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.post_grid_item.view.*


class MainRecyclerAdapter: RecyclerView.Adapter<MainRecyclerAdapter.PostViewHolder>() {
    //ViewHolder
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object: DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
   //Méthode redéfinie qui retourne un ViewHolder et fait une association avec le fichier layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_grid_item,
                parent,
                false
            )
        )
    }
    //Méthode redéfinie qui fait le lien entre les composants et les données à afficher dans le recyclerView
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(data.image).into(image)
            Glide.with(this).load(data.owner?.picture).circleCrop().into(picture)
            text.text=data.text
            tag1.text=data.tags?.get(0)
            tag2.text=data.tags?.get(1)
            tag3.text=data.tags?.get(2)
            publishDate.text=data.publishDate
            firstname.text=data.owner?.firstName
            lastname.text=data.owner?.lastName
            title.text=data.owner?.title


            setOnClickListener{
                onItemClickListener?.let{it(data)}
            }
            trash.setOnLongClickListener {
                val id = data.id
                val intent = Intent(context, PostDeleteActivity::class.java)
                intent.putExtra("id",id)
                context?.startActivity(intent)
                return@setOnLongClickListener true
            }
        }


    }
  //Méthode redéfinie qui a besoin du nombre d’éléments de données à afficher
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit) {
        onItemClickListener = listener
    }
}