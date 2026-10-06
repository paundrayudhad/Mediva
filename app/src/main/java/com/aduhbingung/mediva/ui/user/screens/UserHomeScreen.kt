package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.R
import com.aduhbingung.mediva.ui.theme.MedivaBackground
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme
import com.aduhbingung.mediva.ui.user.components.UserBottomNavBar

@Composable
fun UserHomeScreen(
    userName: String = "Narendra",
    onEmergencyCallClick: () -> Unit = {},
    onViewHistoryClick: () -> Unit = {},
    onTabSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            UserBottomNavBar(
                selectedTab = "Beranda",
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MedivaBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Top Bar: Greeting + Notifications + Profile
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hallo, $userName",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Bagaimana keadaanmu hari ini?",
                        fontSize = 12.sp,
                        color = MedivaTextSecondary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Notification Bell Button
                    Surface(
                        shape = CircleShape,
                        color = MedivaSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            BellIcon(tint = MedivaTextPrimary)
                            // Red Dot
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEF4444))
                            )
                        }
                    }

                    // Avatar Circle
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userName.take(1),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedivaPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hospital & Medical Team Illustration Card (res/drawable/ilustrasi2.jpg)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.ilustrasi2),
                        contentDescription = "Mediva Hospital & Team",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Location Pill below image
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📍", fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Surakarta, Indonesia",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MedivaTextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Hero Emergency Banner ("Butuh Bantuan Darurat?")
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MedivaPrimary,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Butuh Bantuan Darurat?",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Laporkan kondisi darurat dan dapatkan ambulans tercepat.",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onEmergencyCallClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MedivaPrimary
                        )
                    ) {
                        Text(
                            text = "Laporkan Sekarang",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Layanan Section
            Text(
                text = "Layanan",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Card 1: Lapor Darurat
                ServiceMenuCard(
                    title = "Lapor Darurat",
                    icon = { AlertTriangleIcon(tint = Color(0xFFEF4444)) },
                    onClick = onEmergencyCallClick,
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Cari Ambulans
                ServiceMenuCard(
                    title = "Cari Ambulans",
                    icon = { AmbulanceIcon(tint = MedivaPrimary) },
                    onClick = onEmergencyCallClick,
                    modifier = Modifier.weight(1f)
                )

                // Card 3: Riwayat Laporan
                ServiceMenuCard(
                    title = "Riwayat Laporan",
                    icon = { HistoryDocIcon(tint = Color(0xFF10B981)) },
                    onClick = onViewHistoryClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ambulans Terdekat Section
            Text(
                text = "Ambulans Terdekat",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MedivaSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEBF2FE)),
                        contentAlignment = Alignment.Center
                    ) {
                        AmbulanceIcon(tint = MedivaPrimary)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Unit AMB-021 • PMI Surakarta",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedivaTextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Radius 1.8 km • Estimasi tiba 6 menit",
                            fontSize = 11.sp,
                            color = MedivaTextSecondary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFDCFCE7),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
                    ) {
                        Text(
                            text = "Siaga",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ServiceMenuCard(
    title: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MedivaSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MedivaSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MedivaTextPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

/* ==================== HOME ICONS ==================== */

@Composable
private fun BellIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.15f)
            cubicTo(
                size.width * 0.35f, size.height * 0.15f,
                size.width * 0.25f, size.height * 0.45f,
                size.width * 0.18f, size.height * 0.7f
            )
            lineTo(size.width * 0.82f, size.height * 0.7f)
            cubicTo(
                size.width * 0.75f, size.height * 0.45f,
                size.width * 0.65f, size.height * 0.15f,
                center.x, size.height * 0.15f
            )
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.6.dp.toPx()))
        drawArc(
            color = tint,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.4f, size.height * 0.7f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.2f, size.height * 0.18f),
            style = Stroke(width = 1.6.dp.toPx())
        )
    }
}

@Composable
private fun AlertTriangleIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.15f)
            lineTo(size.width * 0.88f, size.height * 0.85f)
            lineTo(size.width * 0.12f, size.height * 0.85f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.6.dp.toPx()))
        drawLine(
            color = tint,
            start = Offset(center.x, size.height * 0.4f),
            end = Offset(center.x, size.height * 0.62f),
            strokeWidth = 1.6.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawCircle(color = tint, radius = 1.dp.toPx(), center = Offset(center.x, size.height * 0.74f))
    }
}

@Composable
private fun AmbulanceIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        // Ambulance body
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.1f, size.height * 0.3f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.8f, size.height * 0.42f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            style = Stroke(width = 1.6.dp.toPx())
        )
        // Wheels
        drawCircle(color = tint, radius = 2.5.dp.toPx(), center = Offset(size.width * 0.3f, size.height * 0.76f))
        drawCircle(color = tint, radius = 2.5.dp.toPx(), center = Offset(size.width * 0.7f, size.height * 0.76f))
        // Cross
        drawLine(
            color = tint,
            start = Offset(size.width * 0.42f, size.height * 0.5f),
            end = Offset(size.width * 0.58f, size.height * 0.5f),
            strokeWidth = 1.6.dp.toPx()
        )
        drawLine(
            color = tint,
            start = Offset(center.x, size.height * 0.42f),
            end = Offset(center.x, size.height * 0.58f),
            strokeWidth = 1.6.dp.toPx()
        )
    }
}

@Composable
private fun HistoryDocIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.6f, size.height * 0.7f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            style = Stroke(width = 1.6.dp.toPx())
        )
        drawLine(
            color = tint,
            start = Offset(size.width * 0.35f, size.height * 0.38f),
            end = Offset(size.width * 0.65f, size.height * 0.38f),
            strokeWidth = 1.6.dp.toPx()
        )
        drawLine(
            color = tint,
            start = Offset(size.width * 0.35f, size.height * 0.54f),
            end = Offset(size.width * 0.65f, size.height * 0.54f),
            strokeWidth = 1.6.dp.toPx()
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun UserHomeScreenPreview() {
    MedivaTheme {
        UserHomeScreen()
    }
}
