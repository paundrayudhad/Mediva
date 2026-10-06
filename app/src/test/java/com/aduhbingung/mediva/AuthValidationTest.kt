package com.aduhbingung.mediva

import com.aduhbingung.mediva.ui.auth.model.AuthValidation
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthValidationTest {
    @Test
    fun testOtpValidation() {
        assertFalse(AuthValidation.isOtpValid("12345"))
        assertTrue(AuthValidation.isOtpValid("123456"))
        assertFalse(AuthValidation.isOtpValid("12345a"))
        assertFalse(AuthValidation.isOtpValid(""))
    }

    @Test
    fun testPasswordCriteria() {
        assertFalse(AuthValidation.isPasswordMin8("Abc1"))
        assertTrue(AuthValidation.isPasswordMin8("Abcdefgh1"))

        assertFalse(AuthValidation.isPasswordMixed("abcdefgh"))
        assertFalse(AuthValidation.isPasswordMixed("ABCDEFGH"))
        assertFalse(AuthValidation.isPasswordMixed("12345678"))
        assertTrue(AuthValidation.isPasswordMixed("Mediva2026"))

        assertTrue(AuthValidation.doPasswordsMatch("Secret123", "Secret123"))
        assertFalse(AuthValidation.doPasswordsMatch("Secret123", "Secret321"))
        assertFalse(AuthValidation.doPasswordsMatch("", ""))
    }
}
