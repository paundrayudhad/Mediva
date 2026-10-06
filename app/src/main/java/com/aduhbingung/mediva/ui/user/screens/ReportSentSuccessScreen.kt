package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.aduhbingung.mediva.ui.theme.MedivaBackground
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme
import com.aduhbingung.mediva.ui.user.components.UserBottomNavBar
import com.aduhbingung.mediva.ui.user.model.EmergencyReportState

@Composable
fun ReportSentSuccessScreen(
    reportState: EmergencyReportState = EmergencyReportState(),
    onTrackStatusClick: () -> Unit = {},
    onViewDetailClick: () -> Unit = {},
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
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            // Blue Circle Checkmark Badge
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(MedivaPrimary),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(28.dp)) {
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

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Laporan berhasil dikirim",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Laporan diteruskan ke pengemudi untuk diproses. Menunggu respons pengemudi.",
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MedivaTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Summary Card
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
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedivaTextPrimary
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFDCFCE7)
                        ) {
                            Text(
                                text = "✓ Terkirim",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF166534),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Jenis kejadian: Kecelakaan lalu lintas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MedivaTextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "📍 ${reportState.locationAddress}",
                        fontSize = 12.sp,
                        color = MedivaTextSecondary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "👥 Jumlah korban: ${reportState.victimCount} orang",
                        fontSize = 12.sp,
                        color = MedivaTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Section: Selanjutnya
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Selanjutnya",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                NextStepItem(
                    icon = "🛡️",
                    title = "Tetap di lokasi yang aman",
                    desc = "Pastikan Anda dan korban aman dari bahaya sekitar."
                )

                Spacer(modifier = Modifier.height(8.dp))

                NextStepItem(
                    icon = "📱",
                    title = "Pastikan nomor HP aktif",
                    desc = "Petugas dapat menghubungi untuk konfirmasi posisi."
                )

                Spacer(modifier = Modifier.height(8.dp))

                NextStepItem(
                    icon = "🧭",
                    title = "Pantau status bantuan",
                    desc = "Notifikasi status muncul begitu unit menuju ke lokasi."
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Primary Button: Pantau Status Laporan
            Button(
                onClick = onTrackStatusClick,
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
                    text = "🔍 Pantau status laporan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Secondary Button: Lihat Detail Laporan
            OutlinedButton(
                onClick = onViewDetailClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MedivaBorder)
            ) {
                Text(
                    text = "Lihat detail laporan",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedivaTextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun NextStepItem(
    icon: String,
    title: String,
    desc: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MedivaSurfaceVariant,
        border = BorderStroke(1.dp, MedivaBorder)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MedivaTextPrimary
                )
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MedivaTextSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ReportSentSuccessScreenPreview() {
    MedivaTheme {
        ReportSentSuccessScreen()
    }
}
