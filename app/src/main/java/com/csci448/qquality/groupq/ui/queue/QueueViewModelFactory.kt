package com.csci448.qquality.groupq.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QueueViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}