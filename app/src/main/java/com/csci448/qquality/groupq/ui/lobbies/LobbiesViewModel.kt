package com.csci448.qquality.groupq.ui.lobbies

import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.LobbyData

class LobbiesViewModel : ViewModel() {

    val lobbies = mutableListOf<LobbyData>()

    init {
        for (i in 0 until 10) {
            lobbies.add(LobbyData("Lobby $i"))
        }
    }

}