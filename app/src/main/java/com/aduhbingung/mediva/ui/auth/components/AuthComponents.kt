package com.aduhbingung.mediva.ui.auth.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aduhbingung.mediva.R
import com.aduhbingung.mediva.ui.theme.MedivaAuthBg
import com.aduhbingung.mediva.ui.theme.MedivaAuthPrimary
import com.aduhbingung.mediva.ui.theme.MedivaBorder
import com.aduhbingung.mediva.ui.theme.MedivaInfoBg
import com.aduhbingung.mediva.ui.theme.MedivaInfoBorder
import com.aduhbingung.mediva.ui.theme.MedivaSuccess
import com.aduhbingung.mediva.ui.theme.MedivaSurface
import com.aduhbingung.mediva.ui.theme.MedivaSurfaceVariant
import com.aduhbingung.mediva.ui.theme.MedivaTextMuted
import com.aduhbingung.mediva.ui.theme.MedivaTextPrimary
import com.aduhbingung.mediva.ui.theme.MedivaTextSecondary

/**
 * Top header with light blue background (#CBDBFD), optional circular back button,
 * and the Mediva ambulance illustration (res/drawable/ilustrasi1.png).
 */
@Composable
fun MedivaHeaderIllustration(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .background(MedivaAuthBg),
        contentAlignment = Alignment.Center
    ) {
        // Back Button if specified
        if (onBackClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 20.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(38.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BackArrowIcon(tint = MedivaTextPrimary)
                    }
                }
            }
        }

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.ilustrasi1),
            contentDescription = "Ilustrasi Mediva",
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(180.dp),
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * White bottom sheet container with smooth rounded top corners (32.dp).
 */
@Composable
fun MedivaWhiteSheetContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = MedivaSurface,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            content()
        }
    }
}

/**
 * Clean Auth TextField with label on top, placeholder, and password toggle.
 */
@Composable
fun MedivaAuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = MedivaTextPrimary,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 13.sp,
                    color = MedivaTextMuted
                )
            },
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MedivaSurfaceVariant,
                unfocusedContainerColor = MedivaSurfaceVariant,
                focusedBorderColor = MedivaAuthPrimary,
                unfocusedBorderColor = MedivaBorder,
                focusedTextColor = MedivaTextPrimary,
                unfocusedTextColor = MedivaTextPrimary
            ),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
            keyboardActions = keyboardActions,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        EyeToggleIcon(visible = passwordVisible, tint = MedivaTextSecondary)
                    }
                }
            } else null
        )
    }
}

/**
 * Primary CTA Button styled with #3F65E4 per spec.
 */
@Composable
fun MedivaAuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MedivaAuthPrimary,
            contentColor = Color.White,
            disabledContainerColor = MedivaAuthPrimary.copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.8f)
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Secondary Outline Button for actions like "Kembali ke masuk" or "Pilih lokasi secara manual".
 */
@Composable
fun MedivaSecondaryOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MedivaBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MedivaTextPrimary
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Social Sign In Buttons for Google and Apple.
 */
@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MedivaBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = MedivaTextPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            GoogleLogoIcon(modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sign in with Google",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MedivaTextPrimary
            )
        }
    }
}

@Composable
fun AppleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MedivaBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = MedivaTextPrimary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AppleLogoIcon(modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Sign in with Apple",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MedivaTextPrimary
            )
        }
    }
}

/**
 * 6-digit OTP input row with individual boxes and auto focus jump.
 */
@Composable
fun OtpInputField(
    code: String,
    onCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    digitCount: Int = 6
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Invisible real text field to capture keyboard input
        BasicTextField(
            value = code,
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                if (digitsOnly.length <= digitCount) {
                    onCodeChange(digitsOnly)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.matchParentSize().clip(RoundedCornerShape(8.dp)),
            decorationBox = { /* Hidden, visual rendered below */ }
        )

        // Render 6 visual boxes
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until digitCount) {
                val digit = code.getOrNull(i)?.toString() ?: ""
                val isFocused = code.length == i

                Box(
                    modifier = Modifier
                        .size(width = 46.dp, height = 54.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MedivaSurfaceVariant)
                        .border(
                            width = if (isFocused) 1.5.dp else 1.dp,
                            color = if (isFocused) MedivaAuthPrimary else MedivaBorder,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = digit,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedivaTextPrimary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Security / info callout card with light blue container and icon.
 */
@Composable
fun SecurityCalloutCard(
    text: String,
    iconType: CalloutIconType = CalloutIconType.LOCK,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MedivaInfoBg)
            .border(1.dp, MedivaInfoBorder, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when (iconType) {
                CalloutIconType.LOCK -> LockIcon(tint = MedivaAuthPrimary, modifier = Modifier.size(20.dp))
                CalloutIconType.MAIL -> MailIcon(tint = MedivaAuthPrimary, modifier = Modifier.size(20.dp))
            }
            Text(
                text = text,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = MedivaTextSecondary
            )
        }
    }
}

enum class CalloutIconType {
    LOCK,
    MAIL
}

/**
 * Checklist requirement item with green checkmark.
 */
@Composable
fun RequirementChecklistItem(
    text: String,
    isMet: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckmarkCircleIcon(isMet = isMet, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isMet) MedivaTextPrimary else MedivaTextSecondary
        )
    }
}

/* ==================== VECTORS & ICONS ==================== */

@Composable
fun BackArrowIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(18.dp)) {
        val path = Path().apply {
            moveTo(size.width * 0.65f, size.height * 0.2f)
            lineTo(size.width * 0.3f, size.height * 0.5f)
            lineTo(size.width * 0.65f, size.height * 0.8f)
        }
        drawPath(
            path = path,
            color = tint,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun EyeToggleIcon(visible: Boolean, tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        // Outline eye
        drawCircle(
            color = tint,
            radius = size.width * 0.18f,
            center = center,
            style = Stroke(width = 1.8.dp.toPx())
        )
        val eyePath = Path().apply {
            moveTo(size.width * 0.15f, size.height * 0.5f)
            quadraticTo(size.width * 0.5f, size.height * 0.2f, size.width * 0.85f, size.height * 0.5f)
            quadraticTo(size.width * 0.5f, size.height * 0.8f, size.width * 0.15f, size.height * 0.5f)
        }
        drawPath(
            path = eyePath,
            color = tint,
            style = Stroke(width = 1.8.dp.toPx())
        )
        if (!visible) {
            drawLine(
                color = tint,
                start = Offset(size.width * 0.2f, size.height * 0.2f),
                end = Offset(size.width * 0.8f, size.height * 0.8f),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

@Composable
fun GoogleLogoIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        // Clean multi-colored G representation
        val stroke = 3.dp.toPx()
        val rect = Size(size.width - stroke, size.height - stroke)
        val offset = Offset(stroke / 2, stroke / 2)

        drawArc(
            color = Color(0xFFEA4335), // Red
            startAngle = 180f,
            sweepAngle = 120f,
            useCenter = false,
            topLeft = offset,
            size = rect,
            style = Stroke(width = stroke)
        )
        drawArc(
            color = Color(0xFFFBBC05), // Yellow
            startAngle = 120f,
            sweepAngle = 60f,
            useCenter = false,
            topLeft = offset,
            size = rect,
            style = Stroke(width = stroke)
        )
        drawArc(
            color = Color(0xFF34A853), // Green
            startAngle = 30f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = offset,
            size = rect,
            style = Stroke(width = stroke)
        )
        drawArc(
            color = Color(0xFF4285F4), // Blue
            startAngle = 330f,
            sweepAngle = 60f,
            useCenter = false,
            topLeft = offset,
            size = rect,
            style = Stroke(width = stroke)
        )
        drawLine(
            color = Color(0xFF4285F4),
            start = Offset(center.x, center.y),
            end = Offset(size.width - stroke / 2, center.y),
            strokeWidth = stroke
        )
    }
}

@Composable
fun AppleLogoIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        // Apple silhouette silhouette drawn via arcs
        drawCircle(
            color = Color.Black,
            radius = size.width * 0.38f,
            center = Offset(center.x, center.y + 1.dp.toPx())
        )
        drawCircle(
            color = Color.White,
            radius = size.width * 0.16f,
            center = Offset(size.width * 0.85f, center.y)
        )
        // Leaf
        drawOval(
            color = Color.Black,
            topLeft = Offset(center.x, 0f),
            size = Size(size.width * 0.25f, size.height * 0.22f)
        )
    }
}

@Composable
fun LockIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        // Shackle
        drawArc(
            color = tint,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.25f, size.height * 0.1f),
            size = Size(size.width * 0.5f, size.height * 0.45f),
            style = Stroke(width = 2.dp.toPx())
        )
        // Body
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.15f, size.height * 0.42f),
            size = Size(size.width * 0.7f, size.height * 0.52f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
    }
}

@Composable
fun MailIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.1f, size.height * 0.2f),
            size = Size(size.width * 0.8f, size.height * 0.6f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            style = Stroke(width = 1.8.dp.toPx())
        )
        val fold = Path().apply {
            moveTo(size.width * 0.12f, size.height * 0.25f)
            lineTo(center.x, size.height * 0.52f)
            lineTo(size.width * 0.88f, size.height * 0.25f)
        }
        drawPath(
            path = fold,
            color = tint,
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun CheckmarkCircleIcon(isMet: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val color = if (isMet) MedivaSuccess else MedivaTextMuted
        val path = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.5f)
            lineTo(size.width * 0.45f, size.height * 0.75f)
            lineTo(size.width * 0.8f, size.height * 0.3f)
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}
