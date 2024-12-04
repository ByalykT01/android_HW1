package com.example.myapplication

import android.util.Patterns

class CredentialsManager {
    private val credentials = mutableMapOf<String, String>()


    fun register(email: String, password: String): Pair<Boolean, String> {
        if (!isEmailValid(email)) {
            return false to "Invalid email format"
        }

        if (!isPasswordValid(password)) {
            return false to "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character"
        }

        val normalizedEmail = email.lowercase()

        if (credentials.containsKey(normalizedEmail)) {
            return false to "Email is already registered"
        }

        credentials[normalizedEmail] = password
        return true to "Registration successful"
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
