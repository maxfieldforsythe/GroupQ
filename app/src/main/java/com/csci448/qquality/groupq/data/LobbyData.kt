package com.csci448.qquality.groupq.data

import java.util.*

data class LobbyData(var name: String, var uuid: String){
    // default constructor needed for Firebase
    constructor() : this("", "")
}