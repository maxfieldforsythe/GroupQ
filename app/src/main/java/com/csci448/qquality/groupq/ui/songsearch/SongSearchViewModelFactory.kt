package com.csci448.qquality.groupq.ui.songsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SongSearchViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}