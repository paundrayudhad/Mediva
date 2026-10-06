package com.aduhbingung.mediva.ui.auth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.GoogleSignInButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthTextField
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaInfoBg
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun RegisterScreen(
    onRegisterSubmit: (name: String, phone: String, email: String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {},
    onGoogleSignInClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("Oktarian Bagas Nugroho") }
    var phone by remember { mutableStateOf("08123456789") }
    var email by remember { mutableStateOf("mediva12@example.com") }
    var password by remember { mutableStateOf("Mediva123!") }
    var agreedToTerms by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaSurface)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Medical Plus Badge
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(MedivaAuthBg.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            MedicalPlusIcon(tint = MedivaAuthPrimary, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Daftar Akun Mediva!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MedivaTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Buat akun untuk mendapatkan akses layanan Mediva.",
            fontSize = 13.sp,
            color = MedivaTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Step 1: Data Diri Stepper Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = MedivaSurfaceVariant,
            border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circle Number 1
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MedivaAuthPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Langkah 1: Data Diri",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary
                    )
                    Text(
                        text = "Lengkapi informasi dasar untuk membuat akun baru",
                        fontSize = 11.sp,
                        color = MedivaTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Form Fields
        MedivaAuthTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Nama lengkap",
            placeholder = "Masukkan Nama lengkap Anda"
        )

        Spacer(modifier = Modifier.height(12.dp))

        MedivaAuthTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "No Telp",
            placeholder = "Masukkan No Telp",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(12.dp))

        MedivaAuthTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Masukkan email anda",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(12.dp))

        MedivaAuthTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Masukkan password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Terms & Conditions Checkbox
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { agreedToTerms = !agreedToTerms },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = { agreedToTerms = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MedivaAuthPrimary,
                    uncheckedColor = MedivaBorder
                )
            )
            Text(
                text = "Saya menyetujui S&K",
                fontSize = 12.sp,
                color = MedivaTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Primary Register Button
        MedivaAuthButton(
            text = "Daftar",
            onClick = { onRegisterSubmit(fullName, phone, email) },
            enabled = fullName.isNotBlank() && phone.isNotBlank() && email.isNotBlank() && agreedToTerms
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
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

        // Google Button
        GoogleSignInButton(onClick = onGoogleSignInClick)

        Spacer(modifier = Modifier.height(20.dp))

        // Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sudah punya akun? ",
                fontSize = 12.sp,
                color = MedivaTextSecondary
            )
            Text(
                text = "Masuk",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaAuthPrimary,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MedicalPlusIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val stroke = 3.dp.toPx()
        // Horizontal bar
        drawLine(
            color = tint,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.2f, center.y),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.8f, center.y),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        // Vertical bar
        drawLine(
            color = tint,
            start = androidx.compose.ui.geometry.Offset(center.x, size.height * 0.2f),
            end = androidx.compose.ui.geometry.Offset(center.x, size.height * 0.8f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun RegisterScreenPreview() {
    MedivaTheme {
        RegisterScreen()
    }
}
