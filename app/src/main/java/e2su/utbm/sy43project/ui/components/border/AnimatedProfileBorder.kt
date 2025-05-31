package e2su.utbm.sy43project.ui.components.border

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import e2su.nooble.models.BorderAnimationType
import e2su.nooble.models.ProfileBorderData
import e2su.nooble.models.ShopItemModel
import e2su.utbm.sy43project.R
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnimatedProfileBorder(
    borderData: ProfileBorderData,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        when (borderData.borderType) {
            BorderAnimationType.STATIC -> StaticBorder(borderData, content)
            BorderAnimationType.COLOR_SHIFT -> ColorShiftBorder(borderData, content)
            BorderAnimationType.FLAME -> FlameBorder(borderData, content)
            BorderAnimationType.PARTICLES -> ParticlesBorder(borderData, content)
            BorderAnimationType.ROTATING -> RotatingBorder(borderData, content)
        }
    }
}

@Composable
fun StaticBorder(
    borderData: ProfileBorderData,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = borderData.colors?.firstOrNull() ?: Color.Gray

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = color,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun ColorShiftBorder(
    borderData: ProfileBorderData,
    content: @Composable () -> Unit
) {
    val colors = borderData.colors ?: listOf(Color.Red, Color.Blue)
    var colorIndex by remember { mutableStateOf(0) }
    val currentColor by animateColorAsState(
        targetValue = colors[colorIndex],
        animationSpec = tween(borderData.animationDuration, easing = LinearEasing)
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(borderData.animationDuration.toLong())
            colorIndex = (colorIndex + 1) % colors.size
        }
    }

    Box {
        // Contour qui change de couleur
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = currentColor,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        // Contenu
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun FlameBorder(
    borderData: ProfileBorderData,
    content: @Composable () -> Unit
) {
    val colors = borderData.colors ?: listOf(Color(0xFFFF5722), Color(0xFFFF9800))
    val infiniteTransition = rememberInfiniteTransition(label = "flamme")
    val animationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(borderData.animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "flamme"
    )

    Box {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = colors,
                    center = center.copy(
                        x = center.x + (radius * 0.1f) * sin((animationValue * 2 * PI).toFloat()),
                        y = center.y + (radius * 0.1f) * cos((animationValue * 2 * PI).toFloat())
                    )
                ),
                radius = radius,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun ParticlesBorder(
    borderData: ProfileBorderData,
    content: @Composable () -> Unit
) {
    // Implémentation simple pour l'exemple
    val color = borderData.colors?.firstOrNull() ?: Color.Cyan
    val infiniteTransition = rememberInfiniteTransition(label = "particules")
    val animationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(borderData.animationDuration),
            repeatMode = RepeatMode.Restart
        ), label = "particules"
    )

    Box {
        // Effet de particules
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2
            val particleCount = 12

            for (i in 0 until particleCount) {
                val angle = i * (2f * kotlin.math.PI / particleCount) + animationValue * 2f * kotlin.math.PI
                val x = center.x + radius * kotlin.math.cos(angle).toFloat()
                val y = center.y + radius * kotlin.math.sin(angle).toFloat()

                drawCircle(
                    color = color,
                    radius = 5.dp.toPx(),
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }

        // Contenu
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun RotatingBorder(
    borderData: ProfileBorderData,
    content: @Composable () -> Unit
) {
    val colors = borderData.colors ?: listOf(Color.Magenta, Color.Blue)
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(borderData.animationDuration, easing = LinearEasing)
        ), label = "rotation"
    )

    Box {
        // Contour rotatif
        Canvas(modifier = Modifier.fillMaxSize()) {
            rotate(rotation) {
                drawCircle(
                    brush = Brush.linearGradient(colors),
                    style = Stroke(width = 8.dp.toPx())
                )
            }
        }

        // Contenu
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
fun StaticBorderPreview(imageResId: Int, modifier: Modifier = Modifier) {
    StaticBorder(
        borderData = ProfileBorderData(
            borderType = BorderAnimationType.STATIC,
            colors = listOf(Color.Yellow)
        ),
        content = {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Profile",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        },
        modifier = modifier.aspectRatio(1f)
    )
}

@Composable
fun BadgePreview(item: ShopItemModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        item.image?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxSize(0.8f)
            )
        }
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


@Composable
fun BorderPreview(
    item: ShopItemModel,
    borderData: ProfileBorderData,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        AnimatedProfileBorder(
            borderData = borderData,
            modifier = Modifier,
            content = {
                Image(
                    painter = painterResource(id = item.image ?: R.drawable.profile),
                    contentDescription = "Profile Preview",
                    modifier = Modifier.fillMaxSize()
                )
            }
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
        StaticBorderPreview(R.drawable.woof)

        ColorShiftBorderPreview(
            ProfileBorderData(
                borderType = BorderAnimationType.COLOR_SHIFT,
                colors = listOf(Color.Blue, Color.Cyan, Color.Magenta, Color.Blue)
            )
        )

        FlameBorderPreview()
    }
}