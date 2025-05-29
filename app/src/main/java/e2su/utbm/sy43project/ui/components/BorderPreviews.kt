package e2su.utbm.sy43project.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import e2su.nooble.models.BorderAnimationType
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ProfileBorderData
import e2su.utbm.sy43project.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StaticBorderPreview(imageRes: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun ColorShiftBorderPreview(borderData: ProfileBorderData, modifier: Modifier = Modifier) {
    val colors = borderData.colors ?: listOf(Color.Blue, Color.Cyan, Color.Magenta, Color.Blue)
    val infiniteTransition = rememberInfiniteTransition(label = "colorTransition")

    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "colorShift"
    )

    // Créer un dégradé circulaire animé
    val gradientBrush = Brush.sweepGradient(
        colors = colors,
        center = Offset.Unspecified
    )

    Box(
        modifier = modifier
            .size(60.dp)
            .drawBehind {
                rotate(animatedProgress * 360) {
                    drawCircle(brush = gradientBrush, radius = size.width / 2)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun FlameBorderPreview(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "flameAnimation")
    val flameColor = listOf(Color(0xFFFF9800), Color(0xFFFF5722), Color(0xFFFF9800))

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        ),
        label = "flameRotation"
    )

    Box(
        modifier = modifier.size(65.dp),
        contentAlignment = Alignment.Center
    ) {
        // Effet de flamme animée
        Box(
            modifier = Modifier
                .size(65.dp * scale)
                .drawBehind {
                    rotate(angle) {
                        // Dessiner des formes de flamme
                        for (i in 0 until 12) {
                            val angleRad = (i * (2 * PI / 12)).toFloat()
                            val radiusOuter = size.width / 2
                            val radiusInner = size.width * 0.42f
                            val waveFactor = (sin(angle * 0.05 + i) * 0.1 + 0.9).toFloat()

                            val x1 = center.x + cos(angleRad) * radiusInner
                            val y1 = center.y + sin(angleRad) * radiusInner
                            val x2 = center.x + cos(angleRad) * radiusOuter * waveFactor
                            val y2 = center.y + sin(angleRad) * radiusOuter * waveFactor

                            drawLine(
                                brush = Brush.linearGradient(flameColor),
                                start = Offset(x1, y1),
                                end = Offset(x2, y2),
                                strokeWidth = 8f
                            )
                        }
                    }
                }
        )

        // Image de profil centrale
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BorderPreviewsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        StaticBorderPreview(R.drawable.profile)

        ColorShiftBorderPreview(
            ProfileBorderData(
                borderType = BorderAnimationType.COLOR_SHIFT,
                colors = listOf(Color.Blue, Color.Cyan, Color.Magenta, Color.Blue)
            )
        )

        FlameBorderPreview()
    }
}