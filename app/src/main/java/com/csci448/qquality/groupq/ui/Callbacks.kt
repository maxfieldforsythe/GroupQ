package com.csci448.qquality.groupq.ui

interface Callbacks {
    fun onLoginPressed()
    fun onRegisterPressed()
    fun onGoToSongSearch()
    fun onLobbyCreated(uuid: String, name: String)
    fun onJoinLobby(uuid: String, name: String)
    fun onCreateNewLobby()
    fun onRegisterSubmitPressed()
}