package com.csci448.qquality.groupq.ui.createlobby

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.ui.Callbacks
import java.util.*

private const val LOG_TAG = "448.CreateLobbyFrag"

class CreateLobbyFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var createLobbyViewModel: CreateLobbyViewModel
    private lateinit var lobbyNameEditText: EditText
    //private lateinit var passwordEditText: EditText
    //private lateinit var reenterPasswordEditText: EditText
    private lateinit var createLobbyButton: Button
    //private lateinit var passwordLayout: LinearLayout
    //private lateinit var reenterPasswordLayout: LinearLayout
    //private lateinit var passwordSwitch: Switch

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
        //passwordEditText = view.findViewById(R.id.lobby_password_edit_text)
        //reenterPasswordEditText = view.findViewById(R.id.lobby_reenter_password_edit_text)
        createLobbyButton = view.findViewById(R.id.create_lobby_button)

        //passwordLayout = view.findViewById(R.id.password_layout)
        //reenterPasswordLayout = view.findViewById(R.id.reenter_password_layout)
        //passwordSwitch = view.findViewById(R.id.password_switch)

        var passwordStuffVisible: Boolean = false

       /* passwordSwitch.setOnClickListener {
            if (!passwordStuffVisible) {
                passwordLayout.visibility = View.VISIBLE
                reenterPasswordLayout.visibility = View.VISIBLE
                passwordStuffVisible = true
            } else {
                passwordLayout.visibility = View.INVISIBLE
                reenterPasswordLayout.visibility = View.INVISIBLE
                passwordStuffVisible = false
            }
        }*/


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
        // get a random UUID for lobby
        val lobbyUUIDString = UUID.randomUUID().toString()
        // get the lobby name
        val lobbyName = lobbyNameEditText.text.toString()

        //TODO check if lobby name already exists
        if (lobbyName.length < 1){
            Toast.makeText(requireContext(), "Add a lobby name!", Toast.LENGTH_SHORT).show()
        } else {
            if (createLobbyViewModel.createLobby(lobbyName, lobbyUUIDString)) {

                //TODO add a lobby password
                // advance to next screen
                callbacks?.onLobbyCreated(lobbyUUIDString, lobbyName)
            } else {
                Toast.makeText(requireContext(), "A lobby with this name already exists!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}