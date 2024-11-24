package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val emailLayout: TextInputLayout = findViewById(R.id.emailInputLayout)
//        val emailEditText: TextInputEditText = findViewById(R.id.emailEditText)
//        val passwordLayout: TextInputLayout = findViewById(R.id.passwordInputLayout)
//        val passwordEditText: TextInputEditText = findViewById(R.id.passwordEditText)
//        val nextButton: MaterialButton = findViewById(R.id.buttonNext)
//
//        nextButton.setOnClickListener {
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            val isEmailValid = validateField(
//                layout = emailLayout,
//                value = email,
//                errorMessage = "Invalid email input"
//            ) { CredentialsManager.isEmailValid(it) }
//
//            val isPasswordValid = validateField(
//                layout = passwordLayout,
//                value = password,
//                errorMessage = "Password cannot be empty"
//            ) { CredentialsManager.isPasswordValid(it) }
//
//            if (isEmailValid && isPasswordValid) {
//                val intent = Intent(this, EmptyActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//    }
//    private fun validateField(
//        layout: TextInputLayout,
//        value: String,
//        errorMessage: String,
//        validationLogic: (String) -> Boolean
//    ): Boolean {
//        return if (!validationLogic(value)) {
//            layout.error = errorMessage
//            layout.isErrorEnabled = true
//            false
//        } else {
//            layout.isErrorEnabled = false
//            layout.error = null
//            true
//        }
//    }
//}