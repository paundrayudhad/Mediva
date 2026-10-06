package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.auth.components.BackArrowIcon
import com.aduhbingung.mediva.ui.theme.MedivaBackground
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme
import com.aduhbingung.mediva.ui.user.components.SlideToConfirmButton
import com.aduhbingung.mediva.ui.user.components.UserBottomNavBar
import com.aduhbingung.mediva.ui.user.model.EmergencyReportState

@Composable
fun ReportReviewScreen(
    reportState: EmergencyReportState = EmergencyReportState(),
    onSlideConfirm: () -> Unit = {},
    onEditReportClick: () -> Unit = {},
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
                        text = "Tinjau Laporan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary
                    )
                    Text(
                        text = "Periksa data sebelum mengirimkan laporan.",
                        fontSize = 11.sp,
                        color = MedivaTextSecondary
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                // Attached Photo Banner
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1E293B)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📸 🚨", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Foto Kejadian Terlampir",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Membantu petugas mengidentifikasi tingkat keparahan",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Structured Summary Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MedivaSurface,
                    border = BorderStroke(1.dp, MedivaBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ringkasan kejadian",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedivaTextPrimary
                            )

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFEBF2FE)
                            ) {
                                Text(
                                    text = reportState.incidentType.displayName,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MedivaPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        SummaryRowItem(
                            icon = "🏷️",
                            label = "Jenis kejadian",
                            value = "Kecelakaan lalu lintas"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        SummaryRowItem(
                            icon = "📍",
                            label = "Lokasi",
                            value = reportState.locationAddress
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        SummaryRowItem(
                            icon = "👥",
                            label = "Jumlah korban",
                            value = "${reportState.victimCount} orang"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        SummaryRowItem(
                            icon = "📝",
                            label = "Keterangan",
                            value = reportState.incidentDetails
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Info Callout
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF0FDF4),
                    border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "ℹ️", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Setelah dikirim, sistem kami akan mencari unit ambulans terdekat untuk segera menuju lokasi kejadian.",
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            color = Color(0xFF166534)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Slide to Send Component
                SlideToConfirmButton(
                    onConfirmed = onSlideConfirm,
                    text = "Geser untuk kirim laporan"
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tarik handle hingga ujung kanan; ketuk saja tidak mengirimkan laporan.",
                    fontSize = 11.sp,
                    color = MedivaTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Edit Report Link
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Edit laporan",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MedivaPrimary,
                        modifier = Modifier
                            .clickable { onEditReportClick() }
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun SummaryRowItem(
    icon: String,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = icon, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MedivaTextSecondary
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MedivaTextPrimary,
                lineHeight = 17.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ReportReviewScreenPreview() {
    MedivaTheme {
        ReportReviewScreen()
    }
}
