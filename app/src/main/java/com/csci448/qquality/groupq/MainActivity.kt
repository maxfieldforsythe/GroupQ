package com.csci448.qquality.groupq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.csci448.qquality.groupq.ui.login.LoginFragment

class MainActivity : AppCompatActivity(), LoginFragment.Callbacks {

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
        // TODO implement change of fragments
        Toast.makeText(this, "onLogin called()", Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterPressed() {
        // TODO implement change of fragment
        Toast.makeText(this, "onRegisterPressed() called", Toast.LENGTH_SHORT)
    }
}
