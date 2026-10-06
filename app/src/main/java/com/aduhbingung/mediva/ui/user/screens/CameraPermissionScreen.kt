package com.aduhbingung.mediva.ui.user.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import com.aduhbingung.mediva.ui.theme.MedivaTheme

@Composable
fun CameraPermissionScreen(
    onGrantCameraClick: () -> Unit = {},
    onPickGalleryClick: () -> Unit = {},
    onContinueWithoutPhotoClick: () -> Unit = {},
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
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                BackArrowIcon(tint = MedivaTextPrimary)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Izin Kamera",
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
            Spacer(modifier = Modifier.height(16.dp))

            // Camera Icon Badge
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEBF2FE)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📷", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Foto membantu petugas memahami kejadian",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Izinkan kamera untuk mengambil foto kondisi korban dan mendampingkannya ke laporan darurat.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = MedivaTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Safety Warning Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MedivaSurfaceVariant,
                border = BorderStroke(1.dp, MedivaBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🛡️", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AMBIL FOTO DARI TEMPAT AMAN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedivaTextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Mengambil foto tidak boleh sampai memperlambat pertolongan atau membahayakan diri Anda.",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = MedivaTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Privacy Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF1F5F9),
                border = BorderStroke(1.dp, MedivaBorder)
            ) {
                Text(
                    text = "Foto hanya digunakan untuk konfirmasi kejadian dan tidak disimpan di galeri publik.",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = MedivaTextSecondary,
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Primary Button: Izinkan Kamera
            Button(
                onClick = onGrantCameraClick,
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
                    text = "Izinkan kamera",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary Button: Pilih dari Galeri
            OutlinedButton(
                onClick = onPickGalleryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MedivaBorder)
            ) {
                Text(
                    text = "Pilih dari galeri",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedivaTextPrimary
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Text Link: Lanjutkan tanpa foto
            Text(
                text = "Lanjutkan tanpa foto",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MedivaPrimary,
                modifier = Modifier
                    .clickable { onContinueWithoutPhotoClick() }
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CameraPermissionScreenPreview() {
    MedivaTheme {
        CameraPermissionScreen()
    }
}
