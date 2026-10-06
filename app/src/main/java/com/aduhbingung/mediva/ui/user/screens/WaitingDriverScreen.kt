package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.BackArrowIcon
import com.aduhbingung.mediva.ui.theme.MedivaBackground
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme
import com.aduhbingung.mediva.ui.user.components.MapCanvasCard
import com.aduhbingung.mediva.ui.user.components.UserBottomNavBar

@Composable
fun WaitingDriverScreen(
    locationAddress: String = "Jl. Slamet Riyadi, Surakarta",
    onStayOnPageClick: () -> Unit = {},
    onBackToHomeClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTabSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            UserBottomNavBar(
                selectedTab = "Lapor",
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
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    BackArrowIcon(tint = MedivaTextPrimary)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Menunggu Pengemudi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary
                    )
                    Text(
                        text = "Laporan terkirim, menunggu konfirmasi driver.",
                        fontSize = 11.sp,
                        color = MedivaTextSecondary
                    )
                }
            }

            // Map Tracker View
            MapCanvasCard(
                locationTitle = locationAddress,
                height = 240.dp
            )

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                // Status Tracker Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MedivaSurface,
                    border = BorderStroke(1.dp, MedivaBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEBF2FE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🚑", fontSize = 18.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = "Menunggu penerimaan",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedivaTextPrimary
                                )
                                Text(
                                    text = "Pengemudi terdekat sedang meninjau...",
                                    fontSize = 11.sp,
                                    color = MedivaTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // 3-step vertical tracker
                        TrackerStepItem(
                            stepNumber = "✓",
                            title = "Laporan terkirim",
                            subtitle = "09:41 WIB • Titik dan kondisi tercatat",
                            isDone = true,
                            isActive = false
                        )

                        TrackerStepItem(
                            stepNumber = "2",
                            title = "Validasi pengemudi",
                            subtitle = "Sedang dicocokkan dengan unit ambulans terdekat",
                            isDone = false,
                            isActive = true
                        )

                        TrackerStepItem(
                            stepNumber = "3",
                            title = "Unit menuju lokasi",
                            subtitle = "Estimasi ETA dan pelacakan langsung",
                            isDone = false,
                            isActive = false,
                            isLast = true
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Callout note
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = MedivaSurfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "ℹ️", fontSize = 13.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Pengemudi akan meninjau kondisi kejadian sebelum menerima laporan dan menuju ke lokasi.",
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MedivaTextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Primary Button: Tetap di halaman ini
                Button(
                    onClick = onStayOnPageClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MedivaPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Tetap di halaman ini",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Secondary Button: Kembali ke Beranda
                OutlinedButton(
                    onClick = onBackToHomeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MedivaBorder)
                ) {
                    Text(
                        text = "Kembali ke Beranda",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MedivaTextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun TrackerStepItem(
    stepNumber: String,
    title: String,
    subtitle: String,
    isDone: Boolean,
    isActive: Boolean,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isDone -> MedivaPrimary
                            isActive -> Color(0xFFEBF2FE)
                            else -> Color(0xFFE2E8F0)
                        }
                    )
                    .border(
                        1.dp,
                        if (isActive) MedivaPrimary else Color.Transparent,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stepNumber,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isDone -> Color.White
                        isActive -> MedivaPrimary
                        else -> MedivaTextSecondary
                    }
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(28.dp)
                        .background(if (isDone) MedivaPrimary else Color(0xFFE2E8F0))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 12.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isActive || isDone) FontWeight.Bold else FontWeight.Medium,
                color = if (isActive || isDone) MedivaTextPrimary else MedivaTextSecondary
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MedivaTextSecondary
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun WaitingDriverScreenPreview() {
    MedivaTheme {
        WaitingDriverScreen()
    }
}
