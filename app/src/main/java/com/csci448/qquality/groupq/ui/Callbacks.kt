package com.csci448.qquality.groupq.ui

interface Callbacks {
    fun onLoginPressed()
    fun onRegisterPressed()
    fun onGoToSongSearch()
    fun onLobbyCreated(name: String)
    fun onJoinLobby(name: String)
    fun onCreateNewLobby()
    fun onRegisterSubmitPressed()
}