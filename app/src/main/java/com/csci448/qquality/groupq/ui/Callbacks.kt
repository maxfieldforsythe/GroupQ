package com.csci448.qquality.groupq.ui

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

interface Callbacks {
    fun onLoginPressed()
    fun onRegisterPressed()
    fun onGoToSongSearch(uuid: String, name: String)
    fun onLobbyCreated(uuid: String, name: String)
    fun onJoinLobby(uuid: String, name: String)
    fun onCreateNewLobby()
    fun onRegisterSubmitPressed()
    fun playYouTubeVideo(videoId: String, youTubePlayerView: YouTubePlayerView)
}