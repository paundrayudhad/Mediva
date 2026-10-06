package com.aduhbingung.mediva.ui.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.AppleSignInButton
import com.aduhbingung.mediva.ui.auth.components.GoogleSignInButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthTextField
import com.aduhbingung.mediva.ui.auth.components.MedivaHeaderIllustration
import com.aduhbingung.mediva.ui.auth.components.MedivaWhiteSheetContainer
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun LoginScreen(
    onLoginClick: (identifier: String) -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    onAppleSignInClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var identifier by remember { mutableStateOf("mediva12@example.com") }
    var password by remember { mutableStateOf("Mediva123!") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaAuthBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header with illustration
        MedivaHeaderIllustration()

        // White bottom sheet container with rounded top
        MedivaWhiteSheetContainer(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Text(
                text = "Selamat Datang Di Mediva!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Login atau Register sekarang untuk menikmati layanan di Mediva",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email or Phone input
            MedivaAuthTextField(
                value = identifier,
                onValueChange = { identifier = it },
                label = "Alamat Email / Nomor Telepon",
                placeholder = "Masukkan alamat email / No. Telp Anda",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password input
            MedivaAuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Kata Sandi",
                placeholder = "Masukkan kata sandi Anda",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Forgot password link
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Lupa kata sandi?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedivaAuthPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onForgotPasswordClick() }
                        .padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primary login CTA (Blue #3F65E4)
            MedivaAuthButton(
                text = "MASUK",
                onClick = { onLoginClick(identifier) },
                enabled = identifier.isNotBlank() && password.isNotBlank()
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Divider: Atau gunakan akun
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = MedivaBorder)
                Text(
                    text = "Atau gunakan akun",
                    fontSize = 11.sp,
                    color = MedivaTextSecondary,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = MedivaBorder)
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Social Buttons
            GoogleSignInButton(onClick = onGoogleSignInClick)
            Spacer(modifier = Modifier.height(10.dp))
            AppleSignInButton(onClick = onAppleSignInClick)

            Spacer(modifier = Modifier.height(22.dp))

            // Footer link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Belum punya akun Mediva? ",
                    fontSize = 12.sp,
                    color = MedivaTextSecondary
                )
                Text(
                    text = "Daftar Sekarang",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaAuthPrimary,
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LoginScreenPreview() {
    MedivaTheme {
        LoginScreen()
    }
}
