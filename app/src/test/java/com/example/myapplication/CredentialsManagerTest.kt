package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {
    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = ""
        val isEmailValid = credentialsManager.isEmailValid(email)
        assertEquals(
            false,
            isEmailValid
        )
    }

    @Test
    fun givenCorrectEmail_thenReturnTrue() {
        val credentialsManager = CredentialsManager()
        val email = "abc@gmail.com"
        val isEmailValid = credentialsManager.isEmailValid(email)
        assertEquals(
            true,
            isEmailValid
        )
    }
    @Test
    fun givenWrongEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()
        val email = "a wrong email"
        val isEmailValid = credentialsManager.isEmailValid(email)
        assertEquals(
            false,
            isEmailValid
        )
    }
}
