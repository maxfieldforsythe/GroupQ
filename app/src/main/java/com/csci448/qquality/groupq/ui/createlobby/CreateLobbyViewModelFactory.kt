package com.csci448.qquality.groupq.ui.createlobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateLobbyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}