package com.csci448.qquality.groupq.ui.lobbies

import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.LobbyData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LobbiesViewModel : ViewModel() {


    // this is allegedly all it takes to get the database
    val database = Firebase.database

    val lobbies = mutableListOf<LobbyData>()

    init {
        for (i in 0 until 10) {
            lobbies.add(LobbyData("Lobby $i"))
        }
    }

}