package com.guresberat.myfirebasedbexercise.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.guresberat.myfirebasedbexercise.Movie
import com.guresberat.myfirebasedbexercise.R

class RegisterFragment : Fragment() {
    private lateinit var save_button: Button
    private lateinit var name_editText: EditText
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_button = view.findViewById(R.id.save_btn)
        name_editText = view.findViewById(R.id.name_editText)
        ratingBar = view.findViewById(R.id.ratingbar)

        save_button.setOnClickListener {
            saveMovie()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun saveMovie() {
        val name = name_editText.text.toString().trim()

        if (name.isEmpty()) {
            name_editText.error = "Please enter a name"
            return
        }


        val ref = FirebaseDatabase.getInstance().getReference("Movies")
        val movieId = ref.push().key
        val movie = Movie(name, ratingBar.rating.toInt())
        ref.child(movieId!!).setValue(movie).addOnCompleteListener {
            Toast.makeText(activity, "Movie added to list", Toast.LENGTH_SHORT).show()
        }
        name_editText.setText("")
    }
}