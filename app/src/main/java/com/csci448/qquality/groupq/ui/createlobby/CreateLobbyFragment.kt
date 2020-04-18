package com.csci448.qquality.groupq.ui.createlobby

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.ui.Callbacks

class CreateLobbyFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var createLobbyViewModel: CreateLobbyViewModel
    private lateinit var lobbyNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var reenterPasswordEditText: EditText
    private lateinit var createLobbyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the ViewModel
        val factory = CreateLobbyViewModelFactory()
        createLobbyViewModel = ViewModelProvider(this, factory)
            .get(CreateLobbyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lobby_creation, container, false)

        lobbyNameEditText = view.findViewById(R.id.lobby_name_edit_text)
        passwordEditText = view.findViewById(R.id.lobby_password_edit_text)
        reenterPasswordEditText = view.findViewById(R.id.lobby_reenter_password_edit_text)
        createLobbyButton = view.findViewById(R.id.create_lobby_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createLobbyButton.setOnClickListener {
            createLobby()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun createLobby() {
        if (lobbyNameEditText.text.length < 1){
            Toast.makeText(requireContext(), "Add a lobby name!", Toast.LENGTH_SHORT).show()
        } else {
            createLobbyViewModel.createLobby(lobbyNameEditText.text.toString())

            // advnace to next screen
            callbacks?.onLobbyCreated()
        }
    }
}