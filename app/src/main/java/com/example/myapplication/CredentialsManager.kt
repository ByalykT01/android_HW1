package com.example.myapplication

import android.util.Patterns

class CredentialsManager {
    fun isEmailValid(mail: String): Boolean {
        val pattern = (
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
                );
        val regex = Regex(pattern)
        return regex.matches(mail)
    }

    fun isPasswordValid(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()])(?=\\S+$).{8,}$"
        val regex = Regex(pattern)
        return regex.matches(password)
    }
}