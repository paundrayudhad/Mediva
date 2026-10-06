package com.aduhbingung.mediva.ui.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.model.AuthRoute
import com.aduhbingung.mediva.ui.auth.model.OtpSource
import com.aduhbingung.mediva.ui.auth.screens.ForgotPasswordScreen
import com.aduhbingung.mediva.ui.auth.screens.LocationPermissionScreen
import com.aduhbingung.mediva.ui.auth.screens.LoginScreen
import com.aduhbingung.mediva.ui.auth.screens.PasswordSuccessScreen
import com.aduhbingung.mediva.ui.auth.screens.RegisterScreen
import com.aduhbingung.mediva.ui.auth.screens.ResetPasswordScreen
import com.aduhbingung.mediva.ui.auth.screens.VerifyOtpScreen
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

/**
 * Root composable managing the entire Auth & Onboarding Flow
 * compliant with `Panduan alur dan akses tracking laporan aktif.pdf`.
 */
@Composable
fun MedivaAuthApp(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<AuthRoute>(AuthRoute.SignIn) }
    val currentRoute = backStack.lastOrNull() ?: AuthRoute.SignIn

    fun navigateTo(route: AuthRoute) {
        backStack.add(route)
    }

    fun popBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    fun resetTo(route: AuthRoute) {
        backStack.clear()
        backStack.add(route)
    }

    // Handle system back button
    BackHandler(enabled = backStack.size > 1) {
        popBack()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            // Demo screen switcher banner for testing & presentation
            DemoQuickScreenBar(
                currentRoute = currentRoute,
                onSelectRoute = { route -> resetTo(route) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentRoute,
                transitionSpec = {
                    (slideInHorizontally { width -> width / 3 } + fadeIn()) togetherWith
                            (slideOutHorizontally { width -> -width / 3 } + fadeOut())
                },
                label = "auth_screen_transition"
            ) { route ->
                when (route) {
                    is AuthRoute.SignIn -> {
                        LoginScreen(
                            onLoginClick = {
                                navigateTo(AuthRoute.LocationPermission)
                            },
                            onRegisterClick = {
                                navigateTo(AuthRoute.SignUp)
                            },
                            onForgotPasswordClick = {
                                navigateTo(AuthRoute.ForgotPassword)
                            }
                        )
                    }

                    is AuthRoute.SignUp -> {
                        RegisterScreen(
                            onRegisterSubmit = { _, _, email ->
                                navigateTo(
                                    AuthRoute.VerifyOtp(
                                        source = OtpSource.SIGN_UP,
                                        destination = email.ifBlank { "mediva12@example.com" }
                                    )
                                )
                            },
                            onLoginClick = {
                                popBack()
                            }
                        )
                    }

                    is AuthRoute.VerifyOtp -> {
                        VerifyOtpScreen(
                            destination = route.destination,
                            source = route.source,
                            onVerifyClick = {
                                if (route.source == OtpSource.SIGN_UP) {
                                    navigateTo(AuthRoute.LocationPermission)
                                } else {
                                    navigateTo(AuthRoute.ResetPassword)
                                }
                            },
                            onBackClick = {
                                popBack()
                            }
                        )
                    }

                    is AuthRoute.LocationPermission -> {
                        LocationPermissionScreen(
                            onGrantPermissionClick = {
                                resetTo(AuthRoute.Home)
                            },
                            onManualLocationClick = {
                                resetTo(AuthRoute.Home)
                            },
                            onBackClick = {
                                popBack()
                            }
                        )
                    }

                    is AuthRoute.ForgotPassword -> {
                        ForgotPasswordScreen(
                            onSubmit = { target ->
                                navigateTo(
                                    AuthRoute.VerifyOtp(
                                        source = OtpSource.PASSWORD_RESET,
                                        destination = target.ifBlank { "mediva12@example.com" }
                                    )
                                )
                            },
                            onBackToLoginClick = {
                                resetTo(AuthRoute.SignIn)
                            }
                        )
                    }

                    is AuthRoute.ResetPassword -> {
                        ResetPasswordScreen(
                            onSavePasswordClick = {
                                navigateTo(AuthRoute.PasswordSuccess)
                            },
                            onBackClick = {
                                popBack()
                            }
                        )
                    }

                    is AuthRoute.PasswordSuccess -> {
                        PasswordSuccessScreen(
                            onBackToLoginClick = {
                                resetTo(AuthRoute.SignIn)
                            }
                        )
                    }

                    is AuthRoute.Home -> {
                        HomePlaceholderScreen(
                            onLogoutClick = {
                                resetTo(AuthRoute.SignIn)
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Placeholder home screen reached after successful authentication & location permission.
 */
@Composable
fun HomePlaceholderScreen(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaSurface)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚑 Mediva Emergency",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MedivaTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Selamat datang! Akun Anda telah terautentikasi dan izin lokasi telah disetujui.",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MedivaTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Example emergency button using MedivaPrimary (#4A7FBF) for non-auth features
        Button(
            onClick = { /* Emergency CTA */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MedivaPrimary, // #4A7FBF per spec for non-auth buttons
                contentColor = Color.White
            )
        ) {
            Text(
                text = "PANGGIL AMBULANS",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF4444),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Keluar Akun",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Floating bar allowing reviewer to quickly jump to any of the 7 screens.
 */
@Composable
private fun DemoQuickScreenBar(
    currentRoute: AuthRoute,
    onSelectRoute: (AuthRoute) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF1F5F9),
        shadowElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
            Text(
                text = "Pratinjau Layar (Demo Switcher):",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextSecondary,
                modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val screens = listOf(
                    "1. Sign In" to AuthRoute.SignIn,
                    "2. Sign Up" to AuthRoute.SignUp,
                    "3. OTP (Daftar)" to AuthRoute.VerifyOtp(OtpSource.SIGN_UP),
                    "4. Izin Lokasi" to AuthRoute.LocationPermission,
                    "5. Lupa Sandi" to AuthRoute.ForgotPassword,
                    "6. Atur Sandi" to AuthRoute.ResetPassword,
                    "7. Sukses" to AuthRoute.PasswordSuccess,
                    "Home" to AuthRoute.Home
                )

                screens.forEach { (label, route) ->
                    val isSelected = when (currentRoute) {
                        is AuthRoute.SignIn -> route is AuthRoute.SignIn
                        is AuthRoute.SignUp -> route is AuthRoute.SignUp
                        is AuthRoute.VerifyOtp -> route is AuthRoute.VerifyOtp
                        is AuthRoute.LocationPermission -> route is AuthRoute.LocationPermission
                        is AuthRoute.ForgotPassword -> route is AuthRoute.ForgotPassword
                        is AuthRoute.ResetPassword -> route is AuthRoute.ResetPassword
                        is AuthRoute.PasswordSuccess -> route is AuthRoute.PasswordSuccess
                        is AuthRoute.Home -> route is AuthRoute.Home
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectRoute(route) },
                        label = { Text(text = label, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MedivaAuthPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun MedivaAuthAppPreview() {
    MedivaTheme {
        MedivaAuthApp()
    }
}
