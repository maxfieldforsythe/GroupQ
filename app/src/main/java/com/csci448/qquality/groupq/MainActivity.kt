package com.csci448.qquality.groupq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.csci448.qquality.groupq.ui.Callbacks
import com.csci448.qquality.groupq.ui.createlobby.CreateLobbyFragment
import com.csci448.qquality.groupq.ui.lobbies.LobbiesFragment
import com.csci448.qquality.groupq.ui.login.LoginFragment
import com.csci448.qquality.groupq.ui.queue.QueueFragment
import com.csci448.qquality.groupq.ui.registration.RegisterFragment
import com.csci448.qquality.groupq.ui.songsearch.SongSearchFragment

class MainActivity : AppCompatActivity(), Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onLoginPressed() {
        // TODO implement user auth handling. Should that be done in separate activity?
        //Toast.makeText(this, "Wrong Fragment launched for testing", Toast.LENGTH_SHORT).show()

        // Don't add to back stack after login
        // TODO launch the correct fragment

        val fragment = LobbiesFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onRegisterPressed() {
        // TODO implement change of fragment
        Toast.makeText(this, "onRegisterPressed() called", Toast.LENGTH_SHORT).show()

        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onRegisterSubmitPressed() {
        Toast.makeText(this, "onRegisterSubmitPressed() called", Toast.LENGTH_SHORT).show()

        val fragment = LobbiesFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onGoToSongSearch() {
        val fragment = SongSearchFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()    }

    /**
     * This function handles moving from the create a lobby screen to the queue screen
     */
    override fun onLobbyCreated() {
        val fragment = QueueFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onJoinLobby() {
        val fragment = QueueFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * This function handles moving from the lobby list screen to the create a lobby screen
     */
    override fun onCreateNewLobby() {
        val fragment = CreateLobbyFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

