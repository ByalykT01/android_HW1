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

class MainActivity : AppCompatActivity() {

    private val emailLayout: TextInputLayout
        get() = findViewById(R.id.emailInputLayout)
    private val emailEditText: TextInputEditText
        get() = findViewById(R.id.emailEditText)
    private val passwordLayout: TextInputLayout
        get() = findViewById(R.id.passwordInputLayout)
    private val passwordEditText: TextInputEditText
        get() = findViewById(R.id.passwordEditText)
    private val nextButton: MaterialButton
        get() = findViewById(R.id.buttonNext)
    private val registerText: MaterialButton
        get() = findViewById(R.id.buttonRegister)

    private val credentialsManager = CredentialsManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
                errorMessage = "Password must contain at least 8 characters, including uppercase, lowercase, number and special character"
            ) { credentialsManager.isPasswordValid(it) }

            if (isEmailValid && isPasswordValid) {
                val intent = Intent(this, EmptyActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        registerText.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
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
