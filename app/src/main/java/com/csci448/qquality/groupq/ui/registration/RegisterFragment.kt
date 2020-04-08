package com.csci448.qquality.groupq.ui.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.csci448.qquality.groupq.R
import com.csci448.qquality.groupq.ui.Callbacks


class RegisterFragment: Fragment() {

    private var callbacks: Callbacks? = null
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerSubmitButton: Button
    private lateinit var tosSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the ViewModel
        val factory = RegisterViewModelFactory()
        registerViewModel = ViewModelProvider(this, factory)
            .get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.registry_screen, container, false)

        registerSubmitButton = view.findViewById(R.id.submit_register_button)
        tosSwitch = view.findViewById((R.id.tos_switch))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        registerSubmitButton.setOnClickListener {

            if (tosSwitch.isChecked) {
                Toast.makeText(
                    context,
                    "Congradulations! You are now registered and ready to use GroupQ!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "You must accept the TOS to register",
                    Toast.LENGTH_SHORT
                ).show()
            }
            callbacks?.onRegisterSubmitPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}