package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme
import com.aduhbingung.mediva.ui.user.components.UserBottomNavBar
import com.aduhbingung.mediva.ui.user.model.EmergencyReportState
import com.aduhbingung.mediva.ui.user.model.IncidentType

@Composable
fun EmergencyReportScreen(
    initialState: EmergencyReportState = EmergencyReportState(),
    onReviewReportClick: (EmergencyReportState) -> Unit = {},
    onAddPhotoClick: () -> Unit = {},
    onChangeLocationClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTabSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var reportState by remember { mutableStateOf(initialState) }

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
                Text(
                    text = "Lapor Darurat",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                // Section 1: Jenis Kejadian
                Text(
                    text = "1. Jenis Kejadian",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IncidentType.entries.forEach { type ->
                        val isSelected = reportState.incidentType == type
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(64.dp)
                                .clickable { reportState = reportState.copy(incidentType = type) },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MedivaPrimary else MedivaSurface,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MedivaPrimary else MedivaBorder
                            ),
                            shadowElevation = if (isSelected) 3.dp else 1.dp
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = type.emoji, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = type.displayName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else MedivaTextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Section 2: Foto Kejadian (Opsional)
                Text(
                    text = "2. Foto Kejadian (Opsional)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                DashedPhotoUploadCard(
                    hasPhoto = reportState.hasPhotoAttachment,
                    onClick = onAddPhotoClick
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Section 3: Lokasi Kejadian
                Text(
                    text = "3. Lokasi Kejadian",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = MedivaSurface,
                    border = BorderStroke(1.dp, MedivaBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEBF2FE)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "📍", fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = reportState.locationAddress,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedivaTextPrimary
                            )
                            Text(
                                text = "Ubah Lokasi",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MedivaPrimary,
                                modifier = Modifier.clickable { onChangeLocationClick() }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Section 4: Detail Kejadian
                Text(
                    text = "4. Detail Kejadian",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = reportState.incidentDetails,
                    onValueChange = { reportState = reportState.copy(incidentDetails = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    placeholder = {
                        Text(
                            text = "Jelaskan apa yang terjadi...",
                            fontSize = 12.sp,
                            color = MedivaTextMuted
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MedivaSurfaceVariant,
                        unfocusedContainerColor = MedivaSurfaceVariant,
                        focusedBorderColor = MedivaPrimary,
                        unfocusedBorderColor = MedivaBorder,
                        focusedTextColor = MedivaTextPrimary,
                        unfocusedTextColor = MedivaTextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Section 5: Jumlah Korban
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "5. Jumlah Korban",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary
                    )

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MedivaSurfaceVariant,
                        border = BorderStroke(1.dp, MedivaBorder)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "—",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedivaTextPrimary,
                                modifier = Modifier
                                    .clickable { reportState = reportState.decrementVictims() }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                            Text(
                                text = "${reportState.victimCount}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedivaTextPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            Text(
                                text = "+",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MedivaTextPrimary,
                                modifier = Modifier
                                    .clickable { reportState = reportState.incrementVictims() }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // CTA Button: Tinjau Laporan (#4A7FBF)
                Button(
                    onClick = { onReviewReportClick(reportState) },
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
                        text = "Tinjau Laporan",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DashedPhotoUploadCard(
    hasPhoto: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(
                width = 1.5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
            )
            drawRoundRect(
                color = Color(0xFFCBD5E1),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                style = stroke
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEBF2FE)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📷", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = if (hasPhoto) "1 Foto terlampir (Ketuk untuk ganti)" else "Tambahkan foto kejadian",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Kamera opsional • screenshot atau galeri",
                    fontSize = 11.sp,
                    color = MedivaTextSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun EmergencyReportScreenPreview() {
    MedivaTheme {
        EmergencyReportScreen()
    }
}
