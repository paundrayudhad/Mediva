package com.aduhbingung.mediva.ui.auth.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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
import com.aduhbingung.mediva.ui.auth.components.MedivaAuthButton
import com.aduhbingung.mediva.ui.auth.components.MedivaSecondaryOutlineButton
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun LocationPermissionScreen(
    onGrantPermissionClick: () -> Unit = {},
    onManualLocationClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                text = "Izin Lokasi",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Location Pin Badge
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(MedivaAuthBg.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                LocationPinIcon(tint = MedivaAuthPrimary, modifier = Modifier.size(30.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bantu petugas menemukan lokasi Anda",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Izinkan akses lokasi saat menggunakan Mediva agar bantuan menuju ke titik kejadian yang tepat.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(26.dp))

            // 3 Benefit Items / Cards
            LocationBenefitItem(
                icon = { DocumentLocationIcon(tint = MedivaAuthPrimary) },
                title = "Lokasi laporan darurat",
                description = "Isi titik kejadian secara otomatis saat konfirmasi sebelum mengirim."
            )

            Spacer(modifier = Modifier.height(12.dp))

            LocationBenefitItem(
                icon = { RouteTrackingIcon(tint = MedivaAuthPrimary) },
                title = "Pelacakan bantuan",
                description = "Tampilkan posisi Anda dan perjalanan ambulans pada peta."
            )

            Spacer(modifier = Modifier.height(12.dp))

            LocationBenefitItem(
                icon = { ShieldPrivacyIcon(tint = MedivaAuthPrimary) },
                title = "Tidak melacak terus-menerus",
                description = "Pelacakan dapat dibatasi dengan memilih titik kejadian secara manual."
            )

            Spacer(modifier = Modifier.height(34.dp))

            // Primary Button: Izinkan Lokasi
            MedivaAuthButton(
                text = "Izinkan lokasi",
                onClick = onGrantPermissionClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Button: Pilih lokasi secara manual
            MedivaSecondaryOutlineButton(
                text = "Pilih lokasi secara manual",
                onClick = onManualLocationClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Caption
            Text(
                text = "Izin dapat diatur melalui pengaturan perangkat.",
                fontSize = 11.sp,
                color = MedivaTextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LocationBenefitItem(
    icon: @Composable () -> Unit,
    title: String,
    description: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MedivaSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = MedivaTextSecondary
                )
            }
        }
    }
}

/* ==================== BENEFIT ICONS ==================== */

@Composable
fun LocationPinIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.9f)
            cubicTo(
                size.width * 0.15f, size.height * 0.55f,
                size.width * 0.15f, size.height * 0.15f,
                center.x, size.height * 0.15f
            )
            cubicTo(
                size.width * 0.85f, size.height * 0.15f,
                size.width * 0.85f, size.height * 0.55f,
                center.x, size.height * 0.9f
            )
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 2.dp.toPx()))
        drawCircle(
            color = tint,
            radius = size.width * 0.14f,
            center = Offset(center.x, size.height * 0.38f)
        )
    }
}

@Composable
private fun DocumentLocationIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.6f, size.height * 0.7f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            style = Stroke(width = 1.8.dp.toPx())
        )
        drawLine(
            color = tint,
            start = Offset(size.width * 0.35f, size.height * 0.4f),
            end = Offset(size.width * 0.65f, size.height * 0.4f),
            strokeWidth = 1.8.dp.toPx()
        )
        drawLine(
            color = tint,
            start = Offset(size.width * 0.35f, size.height * 0.6f),
            end = Offset(size.width * 0.55f, size.height * 0.6f),
            strokeWidth = 1.8.dp.toPx()
        )
    }
}

@Composable
private fun RouteTrackingIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.8f)
            quadraticTo(size.width * 0.5f, size.height * 0.2f, size.width * 0.8f, size.height * 0.2f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.8.dp.toPx()))
        drawCircle(color = tint, radius = 3.dp.toPx(), center = Offset(size.width * 0.2f, size.height * 0.8f))
        drawCircle(color = tint, radius = 3.dp.toPx(), center = Offset(size.width * 0.8f, size.height * 0.2f))
    }
}

@Composable
private fun ShieldPrivacyIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.15f)
            lineTo(size.width * 0.8f, size.height * 0.3f)
            lineTo(size.width * 0.8f, size.height * 0.6f)
            quadraticTo(center.x, size.height * 0.95f, center.x, size.height * 0.95f)
            quadraticTo(size.width * 0.2f, size.height * 0.6f, size.width * 0.2f, size.height * 0.6f)
            lineTo(size.width * 0.2f, size.height * 0.3f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.8.dp.toPx()))
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun LocationPermissionScreenPreview() {
    MedivaTheme {
        LocationPermissionScreen()
    }
}
