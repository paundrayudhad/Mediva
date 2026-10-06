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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.BackArrowIcon
import com.aduhbingung.mediva.ui.auth.components.CalloutIconType
import com.aduhbingung.mediva.ui.auth.components.MailIcon
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaSecondaryOutlineButton
import com.aduhbingung.mediva.ui.auth.components.OtpInputField
import com.aduhbingung.mediva.ui.auth.components.SecurityCalloutCard
import com.aduhbingung.mediva.ui.auth.model.AuthValidation
import com.aduhbingung.mediva.ui.auth.model.OtpSource
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun VerifyOtpScreen(
    destination: String = "mediva12@example.com",
    source: OtpSource = OtpSource.SIGN_UP,
    onVerifyClick: (code: String) -> Unit = {},
    onResendClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var otpCode by remember { mutableStateOf("123456") }
    val isCodeValid = AuthValidation.isOtpValid(otpCode)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaSurface)
            .verticalScroll(rememberScrollState())
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                BackArrowIcon(tint = MedivaTextPrimary)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Verifikasi Akun",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Envelope Badge
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(MedivaAuthBg.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                MailIcon(tint = MedivaAuthPrimary, modifier = Modifier.size(30.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Periksa kode verifikasi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Masukkan 6 digit kode yang dikirim ke",
                fontSize = 13.sp,
                color = MedivaTextSecondary,
                textAlign = TextAlign.Center
            )
            Text(
                text = destination,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 6-digit OTP Box Field
            OtpInputField(
                code = otpCode,
                onCodeChange = { otpCode = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Resend Code Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Belum menerima kode? ",
                    fontSize = 12.sp,
                    color = MedivaTextSecondary
                )
                Text(
                    text = "Kirim ulang",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaAuthPrimary,
                    modifier = Modifier.clickable { onResendClick() }
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Security Callout Card
            SecurityCalloutCard(
                text = "Akan kami konfirmasi langsung untuk menyetujui pendaftaran bila verifikasi berhasil.",
                iconType = CalloutIconType.LOCK
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primary Button: Verifikasi Kode
            MedivaAuthButton(
                text = "Verifikasi kode",
                onClick = { onVerifyClick(otpCode) },
                enabled = isCodeValid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Button: Kembali
            MedivaSecondaryOutlineButton(
                text = "Kembali",
                onClick = onBackClick
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun VerifyOtpScreenPreview() {
    MedivaTheme {
        VerifyOtpScreen()
    }
}
