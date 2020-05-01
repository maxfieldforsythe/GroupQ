package com.csci448.qquality.groupq.ui.lobbies

import android.util.Log
import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.LobbyData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "448.LobbiesVM"

class LobbiesViewModel : ViewModel() {


    // this is allegedly all it takes to get the database
    private val database = Firebase.firestore

    val lobbies = mutableListOf<LobbyData>()

    fun getLobbiesList(): List<LobbyData> {

        // clear out any old data
//        lobbies.clear()

        // get the lobbies from the DB
        val lobbiesData = database.collection("lobbies")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")

                    lobbies.add(LobbyData(document.data.get("name").toString(),
                    document.data.get("uuid").toString()))
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


        return lobbies
    }

}