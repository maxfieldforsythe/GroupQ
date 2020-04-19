package com.csci448.qquality.groupq.ui.createlobby

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val LOG_TAG = "448.CreateLobbyVM"

class CreateLobbyViewModel : ViewModel() {

    // allegedly all it takes to get the firestore database
    private val database = Firebase.firestore

    // public function to add lobby to the database
    // TODO as of right now, lobby creation is just done with the name string
    fun createLobby(name: String): Boolean {
        Log.d(LOG_TAG, "createLobby called()")


        //  TODO don't allow lobby overwrites
//        // check if lobby already exists and return false if so
//        var docExists = false
//        var docRef = database.collection("lobbies").document(name);
//        docRef.get().addOnSuccessListener { _ ->
//            docExists = true
//        }
//
//        database.collection("lobbies").document(name).get()
//            .addOnSuccessListener { task ->
//                if (task.exists()) {
//                    docExists = true
//                }
//            }
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful || task.isComplete) {
//                    docExists = true
//                }
//            }

        if (false) {
            return false
        } else {
            // create the lobby
            // TODO add host_name and uuid fields to lobby data class
            val lobby = hashMapOf(
                "host_name" to "",
                "name" to name,
                "uuid" to ""
            )

            Log.d(LOG_TAG, "lobby map created")

            // push to database
            // TODO should collection path be hardcoded?
            database.collection("lobbies").document(lobby["name"] ?: "Error Lobby")
                .set(lobby)
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

}