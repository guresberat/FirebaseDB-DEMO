package com.guresberat.myfirebasedbexercise.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.guresberat.myfirebasedbexercise.Movie
import com.guresberat.myfirebasedbexercise.OnItemClick
import com.guresberat.myfirebasedbexercise.R
import com.guresberat.myfirebasedbexercise.fragments.adapter.RecyclerViewAdapter

class ListFragment : Fragment(), OnItemClick {
    private lateinit var root: ConstraintLayout
    private lateinit var movieList: MutableList<Movie>
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var list = ArrayList<Movie>()
    private val valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            createData(dataSnapshot)
        }
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
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

        initRecyclerView()

        val ref = FirebaseDatabase.getInstance().getReference("Movies")

        ref.addValueEventListener(valueEventListener)
    }

    private fun createData(dataSnapshot: DataSnapshot) {
        list.clear()
        for (data in dataSnapshot.children) {
            val model = data.getValue(Movie::class.java)
            list.add(model as Movie)
        }
        recyclerViewAdapter.submitList(list)
    }

    private fun initRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.recycler_view)?.apply {
            recyclerViewAdapter = RecyclerViewAdapter(this@ListFragment)
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun removeFromDB(id: String) {
        val ref = FirebaseDatabase.getInstance().getReference("Movies")
        ref.addValueEventListener(valueEventListener)
        ref.child(id).setValue(null)
    }

    override fun onItemClicked(id: String) {
        removeFromDB(id)
    }

}

