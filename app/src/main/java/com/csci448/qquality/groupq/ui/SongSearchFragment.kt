package com.csci448.qquality.groupq.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.csci448.qquality.groupq.R

class SongSearchFragment : Fragment() {
    // TODO nick implement this and layout

    private lateinit var searchRecyclerView: RecyclerView

    private lateinit var searchButton: Button
    private lateinit var searchEditText: EditText
    private lateinit var sourceSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_search, container, false)

        searchButton = view.findViewById(R.id.search_button)
        searchEditText = view.findViewById(R.id.search_edit_text)
        sourceSpinner = view.findViewById(R.id.source_spinner)

        return view
    }
}