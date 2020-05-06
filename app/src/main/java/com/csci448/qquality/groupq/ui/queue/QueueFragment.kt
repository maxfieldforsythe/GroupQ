package com.csci448.qquality.groupq.ui.queue

//import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.data.QueuedSong
import com.csci448.qquality.groupq.data.SongSearchResult
import com.csci448.qquality.groupq.ui.Callbacks
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.youtube.player.YouTubePlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.extensions.LayoutContainer


private const val LOG_TAG = "448.QueueFragment"

private const val LOBBY_UUID_ARG = "lobby_uuid_arg"
private const val LOBBY_NAME_ARG = "lobby_name_arg"

class QueueFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var queueViewModel: QueueViewModel
    private lateinit var queueRecyclerView: RecyclerView
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var adapter: SongQueueAdapter
    private lateinit var searchButton: Button
    //private lateinit var addSongButton: Button
    private lateinit var playButton: AppCompatImageButton
    private lateinit var nextButton: AppCompatImageButton

    private lateinit var lobbyUUIDString: String
    private lateinit var lobbyName: String

    private lateinit var database: FirebaseFirestore

    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_TAG, "onAttach() called")

        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        //set options menu
        setHasOptionsMenu(true)

        // Get the database
        database = Firebase.firestore
        Log.d(LOG_TAG, "database retrieved: ${database.toString()}")

        // Get the ViewModel
        val factory = QueueViewModelFactory()
        queueViewModel = ViewModelProvider(this, factory)
            .get(QueueViewModel::class.java)

        // Get the lobby uuid from the arguments for use in getting the queue
        lobbyUUIDString = arguments?.getString(LOBBY_UUID_ARG) ?: "Error Lobby"
        lobbyName = arguments?.getString(LOBBY_NAME_ARG) ?: "ERROR"
        Log.d(LOG_TAG,"lobbyUUID is: $lobbyUUIDString")

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView() called")

        val view = inflater.inflate(R.layout.queue_screen, container, false)

        // Get the action bar and set the title and display back button
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "${lobbyName}: Queue"
        }

        queueRecyclerView = view.findViewById(R.id.queue_recycler)
        layoutManager = LinearLayoutManager(context)
        queueRecyclerView.layoutManager = layoutManager

        dividerItemDecoration = DividerItemDecoration(queueRecyclerView.context, layoutManager.orientation)
        queueRecyclerView.addItemDecoration(dividerItemDecoration)

        searchButton = view.findViewById(R.id.search_button)
        //addSongButton = view.findViewById(R.id.add_url_button)
        playButton = view.findViewById(R.id.play_button)
        nextButton = view.findViewById(R.id.next_button)

        // add a youtube player to the fragment
        youTubePlayerView = view.findViewById(R.id.youtube_player)
        viewLifecycleOwner.lifecycle.addObserver(youTubePlayerView) // add it as a lifecycle observer
                                                                    // this allows the autoplay feature to work
        // TODO: getFirstSongInQueue()
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            @Override
            fun onReady(youTubePlayer: YouTubePlayer) {
                // TODO: if (the queue is not empty) {
                var videoId = "SYnVYJDxu2Q" // TODO: needs to be set
                youTubePlayer.loadVideo(videoId, 0)
/*
                // TODO: else {
                videoId = "SYnVYJDxu2Q"
                youTubePlayer.loadVideo(videoId)*/
            }
            @Override
            fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                if (state==PlayerConstants.PlayerState.ENDED) {
                    // TODO: moveToNextSong() -> remove current song from queue, getFirstSongInQueue()
                    //  call loadVideo

                }
            }
        })

        playButton.setOnClickListener {

        }

        nextButton.setOnClickListener {
            // TODO: moveToNextSong()
        }

        updateUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOG_TAG, "onViewCreated() called")

        searchButton.setOnClickListener {
            callbacks?.onGoToSongSearch(lobbyUUIDString, lobbyName)
        }
//        addSongButton.setOnClickListener{
//            Toast.makeText(context, "Song added to the queue!", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.queue_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_item_delete -> {
                deleteLobbyAndPopBackstack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // clear action bar text
        (activity as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.app_name)
        }
    }

    private fun updateUI() {

        // query DB for specific queue
        val query = FirebaseFirestore.getInstance()
            .collection("lobbies")
            .document(lobbyUUIDString)
            .collection("queue")
            .orderBy("timeIn")
            .limit(100)

        // configure firestore recycler adapter options
        val builder = FirestoreRecyclerOptions.Builder<QueuedSong>()
            .setQuery(query, QueuedSong::class.java)
            .setLifecycleOwner(this)
            .build()

        val fsAdapter = FireStoreQueueAdapter(builder)
        queueRecyclerView.adapter = fsAdapter


//        //old code
//        val songs = queueViewModel.songs
//        adapter = SongQueueAdapter(songs)
//        queueRecyclerView.adapter = adapter
    }

    //function to delete a lobby and return to the lobbies screen. ust confirm first
    private fun deleteLobbyAndPopBackstack() {
        //show confirmation dialog
        val alert = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_lobby))
            .setMessage(getString(R.string.delete_lobby_msg))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    FirebaseFirestore.getInstance()
                        .collection("lobbies")
                        .document(lobbyUUIDString)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(LOG_TAG, "lobby deleted")
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        .addOnFailureListener {
                            Toast
                                .makeText(requireContext(), "Could not delete this lobby", Toast.LENGTH_SHORT)
                                .show()
                        }
                })
            .setNegativeButton(android.R.string.no, null).show()
    }


    // Firestore adapter to display Q from DB
    private inner class FireStoreQueueAdapter(options: FirestoreRecyclerOptions<QueuedSong>)
        : FirestoreRecyclerAdapter<QueuedSong, FireStoreQueueAdapter.ViewHolder>(options) {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FireStoreQueueAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_queue, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: FireStoreQueueAdapter.ViewHolder,
            position: Int,
            model: QueuedSong
        ) {
            holder.apply {
                titleTextView.text = model.title
            }
        }

        inner class ViewHolder(override val containerView: View)
            : RecyclerView.ViewHolder(containerView), LayoutContainer {

            val titleTextView = containerView.findViewById<TextView>(R.id.song_title)
        }
    }

    /*private fun getCurrentSong() : QueuedSong {
        val query = FirebaseFirestore.getInstance()
            .collection("lobbies")
            .document(lobbyUUIDString)
            .collection("queue")
            .orderBy("timeIn")
            .limit(100)
    }*/


    private inner class SongHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // TODO make the addButton function

        val titleTextView: TextView = itemView.findViewById(R.id.song_title)
        //val artistTextView: TextView = itemView.findViewById(R.id.song_artist)
        val addButton: Button = itemView.findViewById(R.id.add_button)

        fun bind(song: SongSearchResult) {
            titleTextView.text = song.title

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
                .inflate(R.layout.list_item_queue, parent, false)
            return SongHolder(view)
        }

    }


    companion object {

        // new instance function to get a specific lobby
        fun newInstance(lobbyUUIDString: String, lobbyName: String) : QueueFragment {
            val args = Bundle().apply {
                putString(LOBBY_UUID_ARG, lobbyUUIDString)
                putString(LOBBY_NAME_ARG, lobbyName)
            }

            return QueueFragment().apply {
                arguments = args
            }

        }

        val VIDEO_ID: String = ""
    }
}