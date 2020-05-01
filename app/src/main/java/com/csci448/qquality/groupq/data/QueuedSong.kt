package com.csci448.qquality.groupq.data

import java.sql.Timestamp
import java.time.Instant
import java.util.*

data class QueuedSong(var title: String, var url: String, var timeIn: Date, var uuidString: String) {
    // default constructor for firebase
    constructor() : this("","", Date(System.currentTimeMillis()), "")


}