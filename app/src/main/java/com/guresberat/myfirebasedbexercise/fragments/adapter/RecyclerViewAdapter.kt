package com.guresberat.myfirebasedbexercise.fragments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.guresberat.myfirebasedbexercise.Movie
import com.guresberat.myfirebasedbexercise.OnItemClick
import com.guresberat.myfirebasedbexercise.R

class RecyclerViewAdapter(var onItemClick: OnItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MovieViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(movieList: List<Movie>) {
        items = movieList
        notifyDataSetChanged()
    }

    fun clearList(){
        items = emptyList()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        val button = itemView.findViewById<Button>(R.id.recycler_button)

        fun bind(movie: Movie) {
            button.text = "Name:" + movie.name + "\n"  + "Rating:" + movie.rating
            button.setOnClickListener{
                onItemClick.onItemClicked(movie.id)
            }
        }
    }
}