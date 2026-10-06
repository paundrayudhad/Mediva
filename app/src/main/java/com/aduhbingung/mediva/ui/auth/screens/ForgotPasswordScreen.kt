package com.aduhbingung.mediva.ui.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.CalloutIconType
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthTextField
import com.aduhbingung.mediva.ui.auth.components.MedivaHeaderIllustration
import com.aduhbingung.mediva.ui.auth.components.MedivaSecondaryOutlineButton
import com.aduhbingung.mediva.ui.auth.components.MedivaWhiteSheetContainer
import com.aduhbingung.mediva.ui.auth.components.SecurityCalloutCard
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun ForgotPasswordScreen(
    onSubmit: (identifier: String) -> Unit = {},
    onBackToLoginClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var identifier by remember { mutableStateOf("mediva12@example.com") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaAuthBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header with illustration and back button
        MedivaHeaderIllustration(onBackClick = onBackToLoginClick)

        // White bottom sheet container
        MedivaWhiteSheetContainer(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Text(
                text = "Lupa kata sandi?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Masukkan email atau nomor telepon yang terdaftar untuk memulihkan akun Anda.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            MedivaAuthTextField(
                value = identifier,
                onValueChange = { identifier = it },
                label = "Alamat Email / Nomor Telepon",
                placeholder = "Masukkan email atau nomor telepon",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Info callout card
            SecurityCalloutCard(
                text = "Kode verifikasi akan dikirim ke email atau nomor telepon yang Anda masukkan.",
                iconType = CalloutIconType.MAIL
            )

            Spacer(modifier = Modifier.height(26.dp))

            // Primary Button: Kirim kode verifikasi
            MedivaAuthButton(
                text = "Kirim kode verifikasi",
                onClick = { onSubmit(identifier) },
                enabled = identifier.isNotBlank()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Button: Kembali ke masuk
            MedivaSecondaryOutlineButton(
                text = "Kembali ke masuk",
                onClick = onBackToLoginClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Note
            Text(
                text = "Periksa folder spam jika kode belum masuk. Jangan bagikan kode kepada siapa pun.",
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = MedivaTextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ForgotPasswordScreenPreview() {
    MedivaTheme {
        ForgotPasswordScreen()
    }
}
