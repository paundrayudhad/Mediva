package com.aduhbingung.mediva.ui.auth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.BackArrowIcon
import com.aduhbingung.mediva.ui.auth.components.CalloutIconType
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.SecurityCalloutCard
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun PasswordSuccessScreen(
    onBackToLoginClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MedivaSurface)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            IconButton(onClick = onBackToLoginClick) {
                BackArrowIcon(tint = MedivaTextPrimary)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Success Checkmark Badge (Blue Circle #3F65E4 with White Checkmark)
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(MedivaAuthPrimary),
                contentAlignment = Alignment.Center
            ) {
                WhiteCheckmarkIcon(modifier = Modifier.size(28.dp))
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Kata sandi berhasil diubah",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Akun Anda siap digunakan kembali. Masuk dengan kata sandi baru Anda.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Security Callout Card
            SecurityCalloutCard(
                text = "Demi keamanan, gunakan kata sandi baru saat masuk ke Mediva.",
                iconType = CalloutIconType.LOCK
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Primary Button: Kembali ke masuk
            MedivaAuthButton(
                text = "Kembali ke masuk",
                onClick = onBackToLoginClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Bottom Note
            Text(
                text = "Jangan bagikan kata sandi Anda kepada siapa pun, termasuk petugas.",
                fontSize = 11.sp,
                lineHeight = 16.sp,
                color = MedivaTextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun WhiteCheckmarkIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width * 0.22f, size.height * 0.5f)
            lineTo(size.width * 0.44f, size.height * 0.72f)
            lineTo(size.width * 0.78f, size.height * 0.28f)
        }
        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PasswordSuccessScreenPreview() {
    MedivaTheme {
        PasswordSuccessScreen()
    }
}
