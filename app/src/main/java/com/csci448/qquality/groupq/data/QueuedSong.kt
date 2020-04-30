package com.csci448.qquality.groupq.data

import java.sql.Timestamp
import java.time.Instant

data class QueuedSong(var title: String, var url: String, var timeIn: Timestamp, var uuidString: String) {
    // default constructor for firebase
    constructor() : this("","", Timestamp(System.currentTimeMillis()), "")
}