package com.csci448.qquality.groupq.ui

interface Callbacks {
    fun onLoginPressed()
    fun onRegisterPressed()
    fun onGoToSongSearch(uuid: String, name: String)
    fun onLobbyCreated(uuid: String, name: String)
    fun onJoinLobby(uuid: String, name: String)
    fun onCreateNewLobby()
    fun onRegisterSubmitPressed()
}