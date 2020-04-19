package com.csci448.qquality.groupq.ui.lobbies

import android.content.Context
import android.os.Bundle
import android.text.Layout
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
import com.firebase.ui.firestore.SnapshotParser
import com.google.firebase.firestore.DocumentSnapshot
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

        // Cheater way, get the lobbies early so we have them in time to populate recycler view
        // TODO find a better way to observe the data
        lobbiesViewModel.getLobbiesList()
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

        /*I need this function to be somewhere else, because joinButton is out of scope here

        joinButton.setOnClickListener {
            Toast.makeText(context, "Joined a lobby!", Toast.LENGTH_SHORT).show()
            callbacks?.onJoinLobby()
        }*/

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


    private fun updateUI() {
        // replace this with lobbies from DB
//        val lobbies = lobbiesViewModel.lobbies
//
//        adapter = LobbyAdapter(lobbies)
//        lobbiesRecyclerView.adapter = adapter
//

        // Firestore Recycler

        val query = FirebaseFirestore.getInstance().collection("lobbies")
            .orderBy("name")
            .limit(50)

//        //cofigureRecyclerAdapeter options
//        // not sure if it will bind right since doc has3 fields
//        val builder = FirestoreRecyclerOptions.Builder<LobbyData>()
//            .setQuery(query, object : SnapshotParser<LobbyData> {
//                override fun parseSnapshot(snapshot: DocumentSnapshot): LobbyData {
//                    return snapshot.toObject(LobbyData::class.java)!!.also{
//                        // not sure if this line is right/if it does anything but it isnt the problem
//                        it.name = snapshot.get("name").toString()
//                    }
//                }
//            })
//            .setLifecycleOwner(this)
//            .build()


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
            model: LobbyData
        ) {
            Log.d(LOG_TAG, "onBindViewHolder calledd")

            //val lobbyTextView: TextView? = view?.findViewById(R.id.lobby_name)
            //val joinButton: Button? = view?.findViewById(R.id.join_button)


            holder.apply {
                nameTextView.text = model.name
                joinButton.setOnClickListener {
                    Toast.makeText(context, "Joined a lobby!", Toast.LENGTH_SHORT).show()
                    //TODO adapt onJoinLobby to get new instance with a lobby name
                    callbacks?.onJoinLobby()
                }
            }
        }

        inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
            init {
                Log.d(LOG_TAG, "creating viewholder")
            }

            val nameTextView = containerView.findViewById(R.id.lobby_name) as TextView
            val joinButton = containerView.findViewById(R.id.join_button) as Button
        }
    }



}