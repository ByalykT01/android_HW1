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
            assertFalse(
                "Failed for password: $password",
                credentialsManager.isPasswordValid(password)
            )
        }
    }

    @Test
    fun givenPasswordWithoutSpecialCharacter_thenReturnFalse() {
        val passwords = listOf(
            "12aAbBcCtest",
            "NoSpecialChars123"
        )
        passwords.forEach { password ->
            assertFalse(
                "Failed for password: $password",
                credentialsManager.isPasswordValid(password)
            )
        }
    }

    @Test
    fun givenPasswordWithoutNumber_thenReturnFalse() {
        val passwords = listOf(
            "!?aAbBcCtest",
            "NoNumbersHere!"
        )
        passwords.forEach { password ->
            assertFalse(
                "Failed for password: $password",
                credentialsManager.isPasswordValid(password)
            )
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
            assertTrue(
                "Failed for password: $password",
                credentialsManager.isPasswordValid(password)
            )
        }
    }

    @Test
    fun givenNewValidEmailAndPassword_thenRegisterSuccessfully() {
        val email = "test@example.com"
        val password = "Strong@Pass123"

        val (success, message) = credentialsManager.register(email, password)
        assertTrue("Registration failed for valid credentials: $message", success)
    }

    @Test
    fun givenDuplicateEmail_thenReturnError() {
        val email = "test@example.com"
        val password = "Strong@Pass123"

        credentialsManager.register(email, password)
        val (success, message) = credentialsManager.register(email.uppercase(), password)

        assertFalse("Duplicate email registration succeeded unexpectedly", success)
        assertEquals("Email is already registered", message)
    }

    @Test
    fun givenInvalidEmail_thenReturnError() {
        val email = "invalid_email"
        val password = "Strong@Pass123"

        val (success, message) = credentialsManager.register(email, password)
        assertFalse("Invalid email registration succeeded unexpectedly", success)
        assertEquals("Invalid email format", message)
    }

    @Test
    fun givenInvalidPassword_thenReturnError() {
        val email = "test@example.com"
        val password = "weak"

        val (success, message) = credentialsManager.register(email, password)
        assertFalse("Invalid password registration succeeded unexpectedly", success)
        assertEquals(
            "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character",
            message
        )
    }
}