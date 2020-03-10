package com.csci448.qquality.groupq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.csci448.qquality.groupq.ui.Callbacks
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
        Toast.makeText(this, "Wrong Fragment launched for testing", Toast.LENGTH_SHORT).show()

        // Don't add to back stack after login
        // TODO launch the correct fragment
        val fragment = SongSearchFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onRegisterPressed() {
        // TODO implement change of fragment
        Toast.makeText(this, "onRegisterPressed() called", Toast.LENGTH_SHORT).show()

        val fragment = QueueFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
