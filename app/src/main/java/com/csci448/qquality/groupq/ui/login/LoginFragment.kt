package com.csci448.qquality.groupq.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.csci448.qquality.groupq.R

class LoginFragment : Fragment() {

    /*
    * Required Callbacks interface for hosting activities
     */
    interface Callbacks {
        // TODO handle user authentification. For now just a dummy login button
        fun onLoginPressed()
        fun onRegisterPressed()
    }

    private var callbacks: Callbacks? = null
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText


    // Override onAttach to set callbacks to context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register_button)
        usernameEditText = view.findViewById(R.id.username_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            // TODO handle any necessary user auth stuff
            Toast.makeText(context, "Not handling user auth yet", Toast.LENGTH_SHORT).show()

            callbacks?.onLoginPressed()
        }

        registerButton.setOnClickListener {
            Toast.makeText(context, "Register pressed", Toast.LENGTH_SHORT).show()
            callbacks?.onRegisterPressed()
        }
    }

    // Set callbacks to null when detaching
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}