package com.example.myapplication

import android.content.Context

class CredentialsManager(private val context: Context) {
    private val credentials = mutableMapOf<String, String>()

    fun register(email: String, password: String): Pair<Boolean, String> {
        if (!isEmailValid(email)) {
            return false to context.getString(R.string.error_invalid_email_format)
        }

        if (!isPasswordValid(password)) {
            return false to context.getString(R.string.error_password_requirements)
        }

        val normalizedEmail = email.lowercase()

        if (credentials.containsKey(normalizedEmail)) {
            return false to context.getString(R.string.error_email_already_registered)
        }

        credentials[normalizedEmail] = password
        return true to context.getString(R.string.registration_successful)
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = (
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
                )
        val regex = Regex(pattern)
        return regex.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()])(?=\\S+$).{8,}$"
        val regex = Regex(pattern)
        return regex.matches(password)
    }
}