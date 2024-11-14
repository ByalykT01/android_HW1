package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class CredentialsManagerTest {
    @Test
    fun testEmptyEmail() {
        val credentialsManager = CredentialsManager()
        assertEquals(
            false,
            credentialsManager.isEmailValid("")
        )
    }

    @Test
    fun wrongEmailFormat() {
        val credentialsManager = CredentialsManager()
        assertEquals(
            false,
            credentialsManager.isEmailValid("not an email")
        )
    }
}