package com.aduhbingung.mediva.ui.auth.model

object AuthValidation {
    fun isOtpValid(code: String): Boolean =
        code.length == 6 && code.all { it.isDigit() }

    fun isPasswordMin8(password: String): Boolean =
        password.length >= 8

    fun isPasswordMixed(password: String): Boolean =
        password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it.isDigit() }

    fun doPasswordsMatch(p1: String, p2: String): Boolean =
        p1.isNotEmpty() && p1 == p2
}
