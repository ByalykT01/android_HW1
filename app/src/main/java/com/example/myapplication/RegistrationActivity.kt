package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.emailInputLayout)
    private val emailEditText: TextInputEditText
        get() = findViewById(R.id.emailEditText)
    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.passwordInputLayout)
    private val passwordEditText: TextInputEditText
        get() = findViewById(R.id.passwordEditText)
    private val registerButton: MaterialButton
        get() = findViewById(R.id.buttonNext)

    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerButton.setOnClickListener {
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
                errorMessage = "Password must contain at least 8 characters, including uppercase, lowercase, number and special character"
            ) { credentialsManager.isPasswordValid(it) }

            if (isEmailValid && isPasswordValid) {
                val (success, message) = credentialsManager.register(email, password)

                if (success) {
                    // Redirect to LoginActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
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
