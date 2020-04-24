package com.csci448.qquality.groupq.ui.lobbies

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.csci448.qquality.groupq.data.LobbyData
import com.csci448.qquality.groupq.ui.Callbacks
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.extensions.LayoutContainer

private const val LOG_TAG = "448.LobbiesFrag"

class LobbiesFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var lobbiesViewModel: LobbiesViewModel
    private lateinit var lobbiesRecyclerView: RecyclerView
    // private lateinit var adapter: LobbyAdapter
    // private lateinit var joinButton: Button
    private lateinit var hostNewLobbyButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the ViewModel
        val factory = LobbiesViewModelFactory()
        lobbiesViewModel = ViewModelProvider(this, factory)
            .get(LobbiesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lobby_list, container, false)

        lobbiesRecyclerView = view.findViewById(R.id.lobby_recycler)
        hostNewLobbyButton = view.findViewById(R.id.host_new_lobby_button)
        lobbiesRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostNewLobbyButton.setOnClickListener {
            Toast.makeText(context, "Creating a lobby!", Toast.LENGTH_SHORT).show()
            callbacks?.onCreateNewLobby()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    // TODO add option to updateUI with a search string
    private fun updateUI() {

        // Firestore Recycler
        // TODO add a searchbar for limiting the amount of results in the query
        val query = FirebaseFirestore.getInstance().collection("lobbies")
            .orderBy("name")
            .limit(100) //TODO eliminate this in production.sending all results could be dangerous


//        //configureRecyclerAdapeter options
        val builder = FirestoreRecyclerOptions.Builder<LobbyData>()
            .setQuery(query, LobbyData::class.java)
            .setLifecycleOwner(this)
            .build()

        Log.d(LOG_TAG, "builder made")


        val fsAdapter = FirestoreLobbyAdapter(builder)
        lobbiesRecyclerView.adapter = fsAdapter


    }

/* //Here lies the old code for the recycler view w/o the firebase
    private inner class LobbyHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val lobbyTextView: TextView = itemView.findViewById(R.id.lobby_name)
        val joinButton: Button = itemView.findViewById(R.id.join_button)

        fun bind(lobby: LobbyData) {
            lobbyTextView.text = lobby.name
            joinButton.setOnClickListener {
                Toast.makeText(context, "Joined a lobby!", Toast.LENGTH_SHORT).show()
                callbacks?.onJoinLobby()
            }
        }
    }

    private inner class LobbyAdapter(private val lobbies: List<LobbyData>) :
        RecyclerView.Adapter<LobbyHolder>() {
        override fun getItemCount() : Int { return lobbies.size }
        override fun onBindViewHolder(holder: LobbyHolder, position: Int) {
            val lobby = lobbies[position]
            holder.bind(lobby)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_lobby, parent, false)
            return LobbyHolder(view)
        }
    }
 */

    // Firestore adapter to display query results in recycler view
    private inner class FirestoreLobbyAdapter(options: FirestoreRecyclerOptions<LobbyData>) :
    FirestoreRecyclerAdapter<LobbyData, FirestoreLobbyAdapter.ViewHolder>(options) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FirestoreLobbyAdapter.ViewHolder {
            Log.d(LOG_TAG, "onCreateViewHolder called")


            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_lobby, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: FirestoreLobbyAdapter.ViewHolder,
            position: Int,
            lobby: LobbyData
        ) {
            Log.d(LOG_TAG, "onBindViewHolder calledd")

            holder.apply {
                nameTextView.text = lobby.name
                joinButton.setOnClickListener {
                    Toast.makeText(context, "Joined a lobby!", Toast.LENGTH_SHORT).show()

                    // join the lobby if button pressed
                    callbacks?.onJoinLobby(lobby.uuid, lobby.name)
                }
            }
        }

        inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
            // set fields for view holder
            val nameTextView = containerView.findViewById(R.id.lobby_name) as TextView
            val joinButton = containerView.findViewById(R.id.join_button) as Button
        }
    }



}