package com.csci448.qquality.groupq.ui.songsearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.data.QueuedSong
import com.csci448.qquality.groupq.data.SongSearchResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.sql.Timestamp
import java.util.*


// TODO do this differently so source can be used functionally
private val SOURCES = arrayOf("YouTube"/*, "SoundCloud"*/)

private const val LOBBY_NAME_ARG = "lobby_name_arg"
private const val LOBBY_UUID_ARG = "lobby_uuid_arg"
private const val LOG_TAG = "448.SongSearchFrag"

class SongSearchFragment : Fragment() {

    private lateinit var query: String
    private lateinit var result: JSONObject
    private lateinit var items: JSONArray
    private lateinit var songSearchViewModel: SongSearchViewModel
    private lateinit var database: FirebaseFirestore

    private lateinit var lobbyName: String
    private lateinit var lobbyUUIDString: String

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var adapter: SongSearchAdapter

    private lateinit var searchButton2: Button
    private lateinit var searchEditText: EditText
    private lateinit var sourceSpinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the database
        database = Firebase.firestore
        Log.d(LOG_TAG, "database retrieved: ${database.toString()}")


        // Get the ViewModel
        val factory = SongSearchViewModelFactory()
        songSearchViewModel = ViewModelProvider(this, factory)
            .get(SongSearchViewModel::class.java)

        // Get the lobby uuid from the arguments for use in adding to the queue
        lobbyUUIDString = arguments?.getString(LOBBY_UUID_ARG) ?: "Error Lobby"
        lobbyName = arguments?.getString(LOBBY_NAME_ARG) ?: "ERROR"
        Log.d(LOG_TAG,"lobbyUUID is: $lobbyUUIDString")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton2.setOnClickListener {

            query = searchEditText.getText().toString()
            Log.d(LOG_TAG, "Searching for Query")
            songSearchViewModel.songs =  mutableListOf<SongSearchResult>()

            var threadIsFinished = false
            val thread = Thread(Runnable {
                result = JSONObject(URL("https://www.googleapis.com/youtube/v3/search?part=snippet&q=${query}&type=video&maxResults=25&key=AIzaSyCL8nn2bP2rlzgeEserQnQpp0IYepN6Yas").readText())
                items = result.getJSONArray("items")
                for (i in 0 until items.length()) {
                    val c: JSONObject = items.getJSONObject(i)
                    val secondParse = c.getJSONObject("snippet")
                    val title = secondParse.getString("title")
                    val thirdParse = c.getJSONObject("id")
                    val url = thirdParse.getString("videoId")
                    songSearchViewModel.songs.add(SongSearchResult(title, url))

                    if(i+1%5 == 0){
                        Log.d(LOG_TAG,"Added $i songs to list")
                    }
                }
                threadIsFinished = true
            })
            thread.start()


            while(!threadIsFinished){
                Thread.sleep(5)
            }
            //update UI once the thread finishes
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

        // Get the action bar and set the title
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "${lobbyName}: Add a song"
        }

        updateUI()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // clear action bar text
        (activity as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.app_name)
        }
    }


    private fun updateUI() {
        Log.d(LOG_TAG, "updateUI() called")

        val songs = songSearchViewModel.songs
        adapter = SongSearchAdapter(songs)
        searchRecyclerView.adapter = adapter
    }


    // Inner class ViewHolder
    private inner class SongHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val titleTextView: TextView = itemView.findViewById(R.id.song_title)
        //val artistTextView: TextView = itemView.findViewById(R.id.song_artist)
        val addButton: Button = itemView.findViewById(R.id.add_button)

        fun bind(song: SongSearchResult) {
            titleTextView.text = song.title

            // hook up add button to add a queued song to the queue
            addButton.setOnClickListener {
                var queuedSong = QueuedSong()
                queuedSong.title = song.title
                queuedSong.timeIn = Date(System.currentTimeMillis())
                queuedSong.uuidString = UUID.randomUUID().toString()
                // TODO set song URL
                //queuedSong.url = song.url

                // call the VM to add to the database
                if(songSearchViewModel.addToQueue(queuedSong, lobbyUUIDString)) {
                    Toast.makeText(requireContext(), "Added to Q!", Toast.LENGTH_SHORT).show()
                    // return to queue lobby
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Failed to add!", Toast.LENGTH_SHORT).show()
                }


            }
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

    companion object {

        // new instance function to get lobby info to the search fragment
        fun newInstance(lobbyUUIDString: String, lobbyName: String) : SongSearchFragment {
            val args = Bundle().apply {
                putString(LOBBY_UUID_ARG, lobbyUUIDString)
                putString(LOBBY_NAME_ARG, lobbyName)
            }

            return SongSearchFragment().apply {
                arguments = args
            }

        }
    }

}