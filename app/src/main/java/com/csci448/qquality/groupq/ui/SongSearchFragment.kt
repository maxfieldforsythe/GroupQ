package com.csci448.qquality.groupq.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        searchRecyclerView = view.findViewById(R.id.song_seach_recycler)

        return view
    }


    // Inner class view holder
    private inner class SongHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // TODO implement ViewHolder

        val titleTextView: TextView = itemView.findViewById(R.id.song_title)
        val artistTextView: TextView = itemView.findViewById(R.id.song_artist)
        val addButton: Button = itemView.findViewById(R.id.add_button)

        // TODO bind the data
        //fun bind(songData)
    }


    private inner class SongSearchAdapter() :
            RecyclerView.Adapter<SongHolder>() {

        // TODO implement adapter fully
        // TODO override fun getItemCount() : Int {}
        // TODO override fun onBindViewHolder(holder: SongHolder, position: Int) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_song_search, parent, false)
            return SongHolder(view)
        }

    }

}