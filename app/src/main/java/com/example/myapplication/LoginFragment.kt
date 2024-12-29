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

class LoginFragment : Fragment() {
    interface LoginEventHandler {
        fun onLoginSuccess()
        fun onRegisterClicked()
    }

    private var eventHandler: LoginEventHandler? = null
    private lateinit var credentialsManager: CredentialsManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        require(context is LoginEventHandler) {
            "Activity must implement LoginEventHandler"
        }
        eventHandler = context
        credentialsManager = (activity as MainActivity).credentialsManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailLayout = view.findViewById<TextInputLayout>(R.id.emailInputLayout)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordLayout = view.findViewById<TextInputLayout>(R.id.passwordInputLayout)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
        val nextButton = view.findViewById<MaterialButton>(R.id.buttonNext)
        val registerButton = view.findViewById<MaterialButton>(R.id.buttonRegister)

        nextButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val isEmailValid = validateField(
                layout = emailLayout,
                value = email,
                errorMessage = "Invalid email format"
            ) { credentialsManager.isEmailValid(it) }

            val isPasswordValid = validateField(
                layout = passwordLayout,
                value = password,
                errorMessage = "Invalid password format"
            ) { credentialsManager.isPasswordValid(it) }

            if (isEmailValid && isPasswordValid) {
                eventHandler?.onLoginSuccess()
            }
        }

        registerButton.setOnClickListener {
            eventHandler?.onRegisterClicked()
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