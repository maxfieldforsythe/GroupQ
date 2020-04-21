package com.csci448.qquality.groupq.ui.songsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.data.SongSearchResult
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


// TODO do this differently so source can be used functionally
private val SOURCES = arrayOf("YouTube", "SoundCloud")

class SongSearchFragment : Fragment() {
    // TODO nick implement this and layout

    private lateinit var query: String
    private lateinit var result: JSONObject
    private lateinit var items: JSONArray
    private lateinit var songSearchViewModel: SongSearchViewModel

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var adapter: SongSearchAdapter

    private lateinit var searchButton2: Button
    private lateinit var searchEditText: EditText
    private lateinit var sourceSpinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the ViewModel
        val factory = SongSearchViewModelFactory()
        songSearchViewModel = ViewModelProvider(this, factory)
            .get(SongSearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton2.setOnClickListener {

            query = searchEditText.getText().toString()
            songSearchViewModel.songs =  mutableListOf<SongSearchResult>()
            Thread(Runnable {
                result = JSONObject(URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=${query}&type=video&maxResults=25&key=AIzaSyCL8nn2bP2rlzgeEserQnQpp0IYepN6Yas").readText())
                items = result.getJSONArray("items")
                for (i in 0 until items.length()) {
                    val c: JSONObject = items.getJSONObject(i)
                    val secondParse = c.getJSONObject("snippet")
                    val title = secondParse.getString("title");
                    songSearchViewModel.songs.add(SongSearchResult(title))
                }
            }).start()
            updateUI()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_search, container, false)

        searchButton2 = view.findViewById(R.id.search_button2)
        searchEditText = view.findViewById(R.id.search_edit_text)

        sourceSpinner = view.findViewById(R.id.source_spinner)
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, SOURCES)
        sourceSpinner.adapter = spinnerAdapter

        searchRecyclerView = view.findViewById(R.id.song_search_recycler)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
        return view
    }

    private fun updateUI() {
        val songs = songSearchViewModel.songs
        adapter = SongSearchAdapter(songs)
        searchRecyclerView.adapter = adapter
    }


    // Inner class ViewHolder
    private inner class SongHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // TODO make the addButton function

        val titleTextView: TextView = itemView.findViewById(R.id.song_title)
        val artistTextView: TextView = itemView.findViewById(R.id.song_artist)
        val addButton: Button = itemView.findViewById(R.id.add_button)

        fun bind(song: SongSearchResult) {
            titleTextView.text = song.title
        }
    }


    // Inner class Adapter
    private inner class SongSearchAdapter(private val songs: List<SongSearchResult>) :
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