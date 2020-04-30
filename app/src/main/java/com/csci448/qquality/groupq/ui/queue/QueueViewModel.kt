package com.csci448.qquality.groupq.ui.queue

import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.SongSearchResult

class QueueViewModel() : ViewModel(){

    val songs = mutableListOf<SongSearchResult>()

    init {
        for (i in 0 until 10) {
            songs.add(SongSearchResult("Song $i"))
        }
    }
}