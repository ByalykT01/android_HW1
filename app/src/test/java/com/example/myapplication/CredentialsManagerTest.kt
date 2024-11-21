package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {
    private val credentialsManager = CredentialsManager()

    // Email Tests
    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val email = ""
        assertFalse(credentialsManager.isEmailValid(email))
    }

    @Test
    fun givenWrongEmail_thenReturnFalse() {
        val emails = listOf(
            "a wrong email",
            "missingat.com",
            "missing@domain",
            "@missingusername.com"
        )
        emails.forEach { email ->
            assertFalse("Failed for email: $email", credentialsManager.isEmailValid(email))
        }
    }

    @Test
    fun givenCorrectEmail_thenReturnTrue() {
        val emails = listOf(
            "abc@gmail.com",
            "user.name@example.co.uk",
            "user+tag@example.org",
            "firstname.lastname@company.net"
        )
        emails.forEach { email ->
            assertTrue("Failed for email: $email", credentialsManager.isEmailValid(email))
        }
    }

    // Password Tests
    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val password = ""
        assertFalse(credentialsManager.isPasswordValid(password))
    }

    @Test
    fun givenShortPassword_thenReturnFalse() {
        val passwords = listOf(
            "1aAb!",
            "short",
            "noSpecial"
        )
        passwords.forEach { password ->
            assertFalse("Failed for password: $password", credentialsManager.isPasswordValid(password))
        }
    }

    @Test
    fun givenPasswordWithoutSpecialCharacter_thenReturnFalse() {
        val passwords = listOf(
            "12aAbBcCtest",
            "NoSpecialChars123"
        )
        passwords.forEach { password ->
            assertFalse("Failed for password: $password", credentialsManager.isPasswordValid(password))
        }
    }

    @Test
    fun givenPasswordWithoutNumber_thenReturnFalse() {
        val passwords = listOf(
            "!?aAbBcCtest",
            "NoNumbersHere!"
        )
        passwords.forEach { password ->
            assertFalse("Failed for password: $password", credentialsManager.isPasswordValid(password))
        }
    }

    @Test
    fun givenCorrectPassword_thenReturnTrue() {
        val passwords = listOf(
            "!?aAbBcC12",
            "Strong@Pass123",
            "Secure#Password456",
            "C!omplextest789"
        )
        passwords.forEach { password ->
            assertTrue("Failed for password: $password", credentialsManager.isPasswordValid(password))
        }
    }
}