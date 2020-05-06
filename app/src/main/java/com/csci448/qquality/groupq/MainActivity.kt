package com.csci448.qquality.groupq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.csci448.qquality.groupq.ui.Callbacks
import com.csci448.qquality.groupq.ui.createlobby.CreateLobbyFragment
import com.csci448.qquality.groupq.ui.lobbies.LobbiesFragment
import com.csci448.qquality.groupq.ui.login.LoginFragment
import com.csci448.qquality.groupq.ui.queue.QueueFragment
import com.csci448.qquality.groupq.ui.registration.RegisterFragment
import com.csci448.qquality.groupq.ui.songsearch.SongSearchFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

private const val LOG_TAG = "448.MainActivity"

class MainActivity : AppCompatActivity(), Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        setContentView(R.layout.activity_main)

        // Launch into the login fragment
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    /**
     * Moving from LoginFragment to LobbiesFragment
     */
    override fun onLoginPressed() {
        // TODO implement user auth handling. Should that be done in separate activity?
        Log.d(LOG_TAG, "onLoginPressed() called")

        val fragment = LobbiesFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Moving from LoginFragment to RegisterFragment
     */
    override fun onRegisterPressed() {
        Log.d(LOG_TAG, "onRegisterPressed() called")

        val fragment = RegisterFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Moving from RegisterFragment to LobbiesFragment
     * DO NOT add to back stack
     */
    override fun onRegisterSubmitPressed() {
        Log.d(LOG_TAG, "onRegisterSubmitPressed() called")

        val fragment = LobbiesFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Moving from QueueFragment to SongSearchFragment
     */
    override fun onGoToSongSearch(uuid: String, name: String) {
        Log.d(LOG_TAG, "onGoToSongSearch() called")

        val fragment = SongSearchFragment.newInstance(uuid, name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()    }

    /**
     * Moving from CreateLobbyFragment to LobbiesFragment
     * DO NOT add to back stack
     */
    override fun onLobbyCreated(uuid: String, name: String) {
        Log.d(LOG_TAG, "onLobbyCreated() called")

        //  get lobby spcific fragment
        val fragment = QueueFragment.newInstance(uuid, name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Moving from LobbyFragment to QueueFragment
     */
    override fun onJoinLobby(uuid: String, name: String) {
        Log.d(LOG_TAG, "onJoinLobby() called")

        // get lobby specific queue
        val fragment = QueueFragment.newInstance(uuid, name)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Moving from LobbyFragment to CreateLobbyFragment
     */
    override fun onCreateNewLobby() {
        val fragment = CreateLobbyFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Plays a YouTube video in any fragment
     */
    override fun playYouTubeVideo(videoId: String, youTubePlayerView: YouTubePlayerView) {
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
            /*fun onStateChange(youTubePlayer: com.google.android.youtube.player.YouTubePlayer, state: PlayerConstants.PlayerState) {
                if (state== PlayerConstants.PlayerState.ENDED) {
                    // moveToNextSong()
                    // call loadVideo

                }
            }*/
        })
    }
}

