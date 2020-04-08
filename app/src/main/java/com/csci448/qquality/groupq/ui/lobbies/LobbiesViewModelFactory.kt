package com.csci448.qquality.groupq.ui.lobbies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LobbiesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}