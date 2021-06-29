package com.guresberat.myfirebasedbexercise.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.guresberat.myfirebasedbexercise.Movie
import com.guresberat.myfirebasedbexercise.R

class ListFragment : Fragment() {
    private lateinit var root: LinearLayout
    private lateinit var movieList: MutableList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieList = mutableListOf()
        root = view.findViewById(R.id.root)

        val ref = FirebaseDatabase.getInstance().getReference("Movies")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var i = 0
                var list = ArrayList<Movie>()
                for (data in dataSnapshot.children) {
                    var model = data.getValue(Movie::class.java)
                    list.add(model as Movie)
                }
                while (i < list.size) {
                    addButton(list[i].name, list[i].rating.toString())
                    i++
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
    }


    private fun addButton(title: String, rating: String) {
        val button = Button(context)
        button.text = "Name : $title Rating: $rating"
        root.addView(button)
        button.setOnClickListener {
            root.removeView(it)
            //need a function to remove nodes from the db with button
        }
    }
}

