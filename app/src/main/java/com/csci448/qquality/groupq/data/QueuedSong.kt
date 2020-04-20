package com.csci448.qquality.groupq.data

import java.sql.Timestamp
import java.time.Instant

data class QueuedSong(val title: String, val artist: String, val url: String, val timeIn: Timestamp) {
    // default constructor for firebase
    constructor() : this("","","", Timestamp(System.currentTimeMillis()))
}