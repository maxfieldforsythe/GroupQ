package com.csci448.qquality.groupq.ui.songsearch

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.QueuedSong
import com.csci448.qquality.groupq.data.SongSearchResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val LOG_TAG = "448.SongSearchVM"

class SongSearchViewModel() : ViewModel(){

    private val database = Firebase.firestore

    var songs = mutableListOf<SongSearchResult>()


    // Function to add a song to the queue in the database
    fun addToQueue(queuedSong: QueuedSong, lobbyUUIDString: String): Boolean {
        // TODO convert timeIn to string?
        val songMap = hashMapOf(
            "title" to queuedSong.title,
            "url" to queuedSong.url,
            "timeIn" to queuedSong.timeIn,
            "uuid" to queuedSong.uuidString
        )

        database.collection("lobbies")
            .document(lobbyUUIDString)
            .collection("queue")
            .document(songMap["uuid"].toString()) // documents named by uuid
            .set(songMap)
            .addOnSuccessListener { docRef ->
                Log.d(LOG_TAG, "Updated document: $docRef")
            }
            .addOnFailureListener { e ->
                Log.w(LOG_TAG, "Error adding document", e)
            }
            .addOnCanceledListener {
                Log.d(LOG_TAG, "Cancelled")
            }

        // return success
        return true
    }


}