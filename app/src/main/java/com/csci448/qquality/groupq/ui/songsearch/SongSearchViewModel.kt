package com.csci448.qquality.groupq.ui.songsearch

import androidx.lifecycle.ViewModel
import com.csci448.qquality.groupq.data.SongSearchResult

class SongSearchViewModel() : ViewModel(){

    val songs = mutableListOf<SongSearchResult>()

    init {
        for (i in 0 until 25) {
            songs.add(SongSearchResult("Song $i", "Artist ${i/2}"))
        }
    }
}