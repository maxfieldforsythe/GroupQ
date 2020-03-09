package com.csci448.qquality.groupq.ui.queue

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.data.SongSearchResult
import com.csci448.qquality.groupq.ui.login.LoginFragment
import com.csci448.qquality.groupq.ui.songsearch.SongSearchFragment


class QueueFragment: Fragment() {

    private var callbacks: LoginFragment.Callbacks? = null
    private lateinit var queueViewModel: QueueViewModel
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var adapter: SongQueueAdapter
    private lateinit var searchButton: Button


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as LoginFragment.Callbacks
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the ViewModel
        val factory = QueueViewModelFactory()
        queueViewModel = ViewModelProvider(this, factory)
            .get(QueueViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.queue_screen, container, false)



        searchRecyclerView = view.findViewById(R.id.queue_recycler)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchButton = view.findViewById(R.id.search_button)

        updateUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton.setOnClickListener {
            callbacks?.onLoginPressed()
        }
    }




    private fun updateUI() {
        val songs = queueViewModel.songs
        adapter = SongQueueAdapter(songs)
        searchRecyclerView.adapter = adapter
    }

    private inner class SongHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // TODO make the addButton function

        val titleTextView: TextView = itemView.findViewById(R.id.song_title)
        val artistTextView: TextView = itemView.findViewById(R.id.song_artist)
        val addButton: Button = itemView.findViewById(R.id.add_button)

        fun bind(song: SongSearchResult) {
            titleTextView.text = song.title
            artistTextView.text = song.artist
        }
    }


    private inner class SongQueueAdapter(private val songs: List<SongSearchResult>) :
        RecyclerView.Adapter<SongHolder>() {

        override fun getItemCount() : Int { return songs.size }

        override fun onBindViewHolder(holder: SongHolder, position: Int) {
            val song = songs[position]
            holder.bind(song)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_song_search, parent, false)
            return SongHolder(view)
        }

    }


}