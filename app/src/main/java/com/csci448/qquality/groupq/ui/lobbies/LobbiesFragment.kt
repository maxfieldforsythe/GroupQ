package com.csci448.qquality.groupq.ui.lobbies

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
import com.csci448.qquality.groupq.data.LobbyData
import com.csci448.qquality.groupq.ui.Callbacks

class LobbiesFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var lobbiesViewModel: LobbiesViewModel
    private lateinit var lobbiesRecyclerView: RecyclerView
    private lateinit var adapter: LobbyAdapter
    private lateinit var joinButton: Button
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
        joinButton = view.findViewById(R.id.join_button)
        hostNewLobbyButton = view.findViewById(R.id.host_new_lobby_button)
        lobbiesRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        joinButton.setOnClickListener {
            Toast.makeText(context, "Joined a lobby!", Toast.LENGTH_SHORT).show()
            callbacks?.onJoinLobby()
        }

        hostNewLobbyButton.setOnClickListener {
            Toast.makeText(context, "Creating a lobby!", Toast.LENGTH_SHORT).show()
            callbacks?.onCreateNewLobby()
        }

    }

    private fun updateUI() {
        val songs = lobbiesViewModel.lobbies
        adapter = LobbyAdapter(songs)
        lobbiesRecyclerView.adapter = adapter
    }


    private inner class LobbyHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val lobbyTextView: TextView = itemView.findViewById(R.id.lobby_name)
        val joinButton: Button = itemView.findViewById(R.id.join_button)

        fun bind(lobby: LobbyData) {
            lobbyTextView.text = lobby.name
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }


}