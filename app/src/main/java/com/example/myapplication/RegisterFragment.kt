package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {
    interface RegisterEventHandler {
        fun onRegisterSuccess()
    }

    private var eventHandler: RegisterEventHandler? = null
    private lateinit var credentialsManager: CredentialsManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        require(context is RegisterEventHandler) {
            "Activity must implement RegisterEventHandler"
        }
        eventHandler = context
        credentialsManager = (activity as MainActivity).credentialsManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailLayout = view.findViewById<TextInputLayout>(R.id.emailInputLayout)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
        val registerButton = view.findViewById<MaterialButton>(R.id.buttonNext)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val isEmailValid = validateField(
                layout = emailLayout,
                value = email,
                errorMessage = getString(R.string.error_invalid_email_format)
            ) { credentialsManager.isEmailValid(it) }

            val isPasswordValid = validateField(
                layout = passwordLayout,
                value = password,
                errorMessage = getString(R.string.error_password_requirements)
            ) { credentialsManager.isPasswordValid(it) }

            if (isEmailValid && isPasswordValid) {
                val (success, message) = credentialsManager.register(email, password)
                if (success) {
                    eventHandler?.onRegisterSuccess()
                } else {
                    emailLayout.error = message
                    emailLayout.isErrorEnabled = true
                }
            }
        }
    }

    private fun validateField(
        layout: TextInputLayout,
        value: String,
        errorMessage: String,
        validationLogic: (String) -> Boolean
    ): Boolean {
        return if (!validationLogic(value)) {
            layout.error = errorMessage
            layout.isErrorEnabled = true
            false
        } else {
            layout.isErrorEnabled = false
            layout.error = null
            true
        }
    }
}