package com.aduhbingung.mediva.ui.auth.model

enum class OtpSource {
    SIGN_UP,
    PASSWORD_RESET
}

sealed interface AuthRoute {
    data object SignIn : AuthRoute
    data object SignUp : AuthRoute
    data class VerifyOtp(
        val source: OtpSource = OtpSource.SIGN_UP,
        val destination: String = "mediva12@example.com"
    ) : AuthRoute
    data object LocationPermission : AuthRoute
    data object ForgotPassword : AuthRoute
    data object ResetPassword : AuthRoute
    data object PasswordSuccess : AuthRoute
    data object Home : AuthRoute
}
