package com.csci448.qquality.groupq.ui.songsearch

import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.SongSearchResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SongSearchViewModel() : ViewModel(){

    private val database = Firebase.firestore

    val songs = mutableListOf<SongSearchResult>()

    init {
        for (i in 0 until 25) {
            songs.add(SongSearchResult("Song $i", "Artist ${i/2}"))
        }
    }

    // Function to add a song to the queue in the database
    fun addToQueue(lobbyName: String) {
        // TODO add to database
    }
}