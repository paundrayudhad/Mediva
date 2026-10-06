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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthTextField
import com.aduhbingung.mediva.ui.auth.components.MedivaHeaderIllustration
import com.aduhbingung.mediva.ui.auth.components.MedivaWhiteSheetContainer
import com.aduhbingung.mediva.ui.auth.components.RequirementChecklistItem
import com.aduhbingung.mediva.ui.auth.model.AuthValidation
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun ResetPasswordScreen(
    onSavePasswordClick: (newPassword: String) -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf("Mediva2026!") }
    var confirmPassword by remember { mutableStateOf("Mediva2026!") }

    val hasMin8 = AuthValidation.isPasswordMin8(newPassword)
    val hasMixed = AuthValidation.isPasswordMixed(newPassword)
    val match = AuthValidation.doPasswordsMatch(newPassword, confirmPassword)
    val isAllValid = hasMin8 && hasMixed && match

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaAuthBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header with illustration and back button
        MedivaHeaderIllustration(onBackClick = onBackClick)

        // White bottom sheet container
        MedivaWhiteSheetContainer(
            modifier = Modifier.weight(1f, fill = false)
        ) {
            Text(
                text = "Atur kata sandi baru",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Gunakan kata sandi yang kuat dan berbeda dari kata sandi sebelumnya.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            MedivaAuthTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = "Kata sandi baru",
                placeholder = "Masukkan kata sandi baru",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            MedivaAuthTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Konfirmasi kata sandi baru",
                placeholder = "Ulangi kata sandi baru",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Real-time Requirements Checklist
            RequirementChecklistItem(
                text = "Minimal 8 karakter",
                isMet = hasMin8
            )
            RequirementChecklistItem(
                text = "Gabungkan huruf besar, huruf kecil, dan angka",
                isMet = hasMixed
            )
            RequirementChecklistItem(
                text = "Kedua kata sandi harus sama",
                isMet = match
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Primary CTA: Simpan kata sandi baru
            MedivaAuthButton(
                text = "Simpan kata sandi baru",
                onClick = { onSavePasswordClick(newPassword) },
                enabled = isAllValid
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ResetPasswordScreenPreview() {
    MedivaTheme {
        ResetPasswordScreen()
    }
}
