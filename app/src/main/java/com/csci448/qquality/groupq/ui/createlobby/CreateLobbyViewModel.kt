package com.csci448.qquality.groupq.ui.createlobby

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateLobbyViewModel : ViewModel() {

    // allegedly all it takes to get the database
    val database = Firebase.database

}