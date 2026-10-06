package com.aduhbingung.mediva.ui.user.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.ui.user.model.EmergencyReportState
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaPrimary
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary
import kotlin.math.roundToInt

/**
 * Bottom Navigation Bar for User Role (3 items: Beranda, Lapor, Akun)
 * strictly per `Panduan alur dan akses tracking laporan aktif.pdf`.
 */
@Composable
fun UserBottomNavBar(
    selectedTab: String = "Beranda",
    onTabSelected: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MedivaSurface,
        shadowElevation = 10.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MedivaBorder.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 1: Beranda
            UserNavItem(
                title = "Beranda",
                isSelected = selectedTab == "Beranda",
                icon = { isSelected ->
                    HomeNavIcon(tint = if (isSelected) MedivaPrimary else MedivaTextMuted)
                },
                onClick = { onTabSelected("Beranda") }
            )

            // Tab 2: Lapor
            UserNavItem(
                title = "Lapor",
                isSelected = selectedTab == "Lapor",
                icon = { isSelected ->
                    ReportNavIcon(tint = if (isSelected) MedivaPrimary else MedivaTextMuted)
                },
                onClick = { onTabSelected("Lapor") }
            )

            // Tab 3: Akun
            UserNavItem(
                title = "Akun",
                isSelected = selectedTab == "Akun",
                icon = { isSelected ->
                    AccountNavIcon(tint = if (isSelected) MedivaPrimary else MedivaTextMuted)
                },
                onClick = { onTabSelected("Akun") }
            )
        }
    }
}

@Composable
private fun UserNavItem(
    title: String,
    isSelected: Boolean,
    icon: @Composable (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon(isSelected)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) MedivaPrimary else MedivaTextMuted
        )
    }
}

/**
 * Slide to Confirm component ("Geser untuk kirim laporan").
 * Must be dragged across to the end to trigger confirmation.
 */
@Composable
fun SlideToConfirmButton(
    onConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Geser untuk kirim laporan"
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0xFFEBF2FE))
            .border(1.dp, Color(0xFFD6E4FC), RoundedCornerShape(28.dp))
    ) {
        val density = LocalDensity.current
        val thumbSizePx = with(density) { 48.dp.toPx() }
        val maxDragPx = constraints.maxWidth - thumbSizePx - with(density) { 8.dp.toPx() }

        var dragOffsetPx by remember { mutableFloatStateOf(0f) }

        val animatedOffsetPx by animateFloatAsState(
            targetValue = dragOffsetPx,
            label = "slide_offset"
        )

        // Text in center
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MedivaPrimary,
                modifier = Modifier.padding(start = 28.dp)
            )
        }

        // Draggable Circular Thumb
        Box(
            modifier = Modifier
                .offset { IntOffset(x = animatedOffsetPx.roundToInt() + 8, y = 4) }
                .size(48.dp)
                .clip(CircleShape)
                .background(MedivaPrimary)
                .shadow(4.dp, CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newOffset = (dragOffsetPx + delta).coerceIn(0f, maxDragPx)
                        dragOffsetPx = newOffset
                    },
                    onDragStopped = {
                        val fraction = if (maxDragPx > 0) dragOffsetPx / maxDragPx else 0f
                        if (EmergencyReportState.isSlideCompleted(fraction)) {
                            dragOffsetPx = maxDragPx
                            onConfirmed()
                        } else {
                            dragOffsetPx = 0f
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            SlideRightArrowIcon(tint = Color.White)
        }
    }
}

/**
 * Visual Map Canvas displaying Surakarta map simulation with live pin & accuracy badge.
 */
@Composable
fun MapCanvasCard(
    locationTitle: String = "Jl. Slamet Riyadi, Surakarta",
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 220.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(Color(0xFFE2E8F0))
    ) {
        // Grid Map canvas drawing streets & blocks
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background city block color
            drawRect(color = Color(0xFFE5E7EB))

            // Road strokes
            val roadColor = Color.White
            val roadWidth = 14.dp.toPx()
            val smallRoadWidth = 6.dp.toPx()

            // Main Avenue
            drawLine(
                color = roadColor,
                start = Offset(0f, size.height * 0.45f),
                end = Offset(size.width, size.height * 0.45f),
                strokeWidth = roadWidth
            )
            // Secondary roads
            drawLine(
                color = roadColor,
                start = Offset(size.width * 0.35f, 0f),
                end = Offset(size.width * 0.35f, size.height),
                strokeWidth = roadWidth
            )
            drawLine(
                color = roadColor,
                start = Offset(size.width * 0.75f, 0f),
                end = Offset(size.width * 0.75f, size.height),
                strokeWidth = smallRoadWidth
            )
            drawLine(
                color = roadColor,
                start = Offset(0f, size.height * 0.8f),
                end = Offset(size.width, size.height * 0.8f),
                strokeWidth = smallRoadWidth
            )

            // Pulsing accuracy circle around center
            drawCircle(
                color = MedivaPrimary.copy(alpha = 0.2f),
                radius = 36.dp.toPx(),
                center = Offset(size.width * 0.52f, size.height * 0.45f)
            )
            drawCircle(
                color = MedivaPrimary.copy(alpha = 0.4f),
                radius = 20.dp.toPx(),
                center = Offset(size.width * 0.52f, size.height * 0.45f)
            )
        }

        // Location Pin Marker
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 6.dp, y = (-12).dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MedivaPrimary,
                shadowElevation = 6.dp,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    LocationPinDotIcon(tint = Color.White)
                }
            }
        }

        // Top-Right Chip: "Lokasi aktif"
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(14.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFDCFCE7), // Soft green
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF86EFAC))
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF16A34A))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Lokasi aktif",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF166534)
                )
            }
        }

        // Bottom Map Location Pill
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            shape = RoundedCornerShape(10.dp),
            color = Color.White.copy(alpha = 0.95f),
            shadowElevation = 3.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "📍", fontSize = 11.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = locationTitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MedivaTextPrimary
                )
            }
        }
    }
}

/* ==================== VECTORS & ICONS ==================== */

@Composable
fun HomeNavIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.15f)
            lineTo(size.width * 0.85f, size.height * 0.48f)
            lineTo(size.width * 0.85f, size.height * 0.85f)
            lineTo(size.width * 0.15f, size.height * 0.85f)
            lineTo(size.width * 0.15f, size.height * 0.48f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
fun ReportNavIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.15f)
            lineTo(size.width * 0.88f, size.height * 0.85f)
            lineTo(size.width * 0.12f, size.height * 0.85f)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
        drawLine(
            color = tint,
            start = Offset(center.x, size.height * 0.42f),
            end = Offset(center.x, size.height * 0.62f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawCircle(color = tint, radius = 1.2.dp.toPx(), center = Offset(center.x, size.height * 0.74f))
    }
}

@Composable
fun AccountNavIcon(tint: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawCircle(
            color = tint,
            radius = size.width * 0.22f,
            center = Offset(center.x, size.height * 0.32f),
            style = Stroke(width = 1.8.dp.toPx())
        )
        drawArc(
            color = tint,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.15f, size.height * 0.55f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.7f, size.height * 0.55f),
            style = Stroke(width = 1.8.dp.toPx())
        )
    }
}

@Composable
fun SlideRightArrowIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.35f, size.height * 0.25f)
            lineTo(size.width * 0.65f, size.height * 0.5f)
            lineTo(size.width * 0.35f, size.height * 0.75f)
        }
        drawPath(path = path, color = tint, style = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
fun LocationPinDotIcon(tint: Color) {
    Canvas(modifier = Modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(center.x, size.height * 0.85f)
            cubicTo(
                size.width * 0.2f, size.height * 0.55f,
                size.width * 0.2f, size.height * 0.2f,
                center.x, size.height * 0.2f
            )
            cubicTo(
                size.width * 0.8f, size.height * 0.2f,
                size.width * 0.8f, size.height * 0.55f,
                center.x, size.height * 0.85f
            )
            close()
        }
        drawPath(path = path, color = tint)
        drawCircle(color = Color(0xFF4A7FBF), radius = 2.5.dp.toPx(), center = Offset(center.x, size.height * 0.42f))
    }
}
