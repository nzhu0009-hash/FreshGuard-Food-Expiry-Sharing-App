package com.example.foodwasteapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun AuthIllustration(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                            )
                        )
                    )
            )

            FoodWasteHeroArt(
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
            )
        }

        Surface(
            shape = RoundedCornerShape(999.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
            tonalElevation = 3.dp
        ) {
            Text(
                text = "Reduce food waste",
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FoodWasteHeroArt(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        CompostBin(
            modifier = Modifier
                .size(118.dp)
                .align(Alignment.Center)
        )

        ScrapMotif(
            type = ScrapType.Leaf,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.TopCenter)
                .offset(x = (-8).dp, y = 8.dp)
                .graphicsLayer(rotationZ = -16f)
        )
        ScrapMotif(
            type = ScrapType.Peel,
            modifier = Modifier
                .size(44.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-18).dp, y = 36.dp)
                .graphicsLayer(rotationZ = 18f)
        )
        ScrapMotif(
            type = ScrapType.TeaBag,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-2).dp, y = (-38).dp)
                .graphicsLayer(rotationZ = -12f)
        )
        ScrapMotif(
            type = ScrapType.Banana,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterEnd)
                .offset(x = (-10).dp, y = 30.dp)
                .graphicsLayer(rotationZ = 8f)
        )
        ScrapMotif(
            type = ScrapType.AppleCore,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (-28).dp, y = (-10).dp)
                .graphicsLayer(rotationZ = -20f)
        )
        ScrapMotif(
            type = ScrapType.Leaf,
            modifier = Modifier
                .size(34.dp)
                .align(Alignment.BottomCenter)
                .offset(x = 40.dp, y = (-2).dp)
                .graphicsLayer(rotationZ = 26f)
        )
        ScrapMotif(
            type = ScrapType.Cheese,
            modifier = Modifier
                .size(44.dp)
                .align(Alignment.BottomStart)
                .offset(x = 26.dp, y = (-16).dp)
                .graphicsLayer(rotationZ = 10f)
        )
        ScrapMotif(
            type = ScrapType.TeaBag,
            modifier = Modifier
                .size(34.dp)
                .align(Alignment.CenterStart)
                .offset(x = 8.dp, y = 32.dp)
                .graphicsLayer(rotationZ = 14f)
        )
        ScrapMotif(
            type = ScrapType.AppleCore,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart)
                .offset(x = 6.dp, y = (-46).dp)
                .graphicsLayer(rotationZ = -8f)
        )
        ScrapMotif(
            type = ScrapType.Peel,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopStart)
                .offset(x = 28.dp, y = 42.dp)
                .graphicsLayer(rotationZ = -28f)
        )

        SmallBerry(
            modifier = Modifier.align(Alignment.TopStart).offset(x = 72.dp, y = 14.dp)
        )
        SmallBerry(
            modifier = Modifier.align(Alignment.TopEnd).offset(x = (-46).dp, y = 88.dp)
        )
        SmallBerry(
            modifier = Modifier.align(Alignment.BottomEnd).offset(x = (-62).dp, y = (-34).dp)
        )
        SmallLeaf(
            modifier = Modifier.align(Alignment.BottomStart).offset(x = 74.dp, y = (-36).dp)
        )
        SmallLeaf(
            modifier = Modifier.align(Alignment.CenterEnd).offset(x = (-38).dp, y = 2.dp)
        )
    }
}

@Composable
private fun CompostBin(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        drawRoundRect(
            color = Color(0xFF7BA734),
            topLeft = Offset(w * 0.18f, h * 0.24f),
            size = Size(w * 0.64f, h * 0.58f),
            cornerRadius = CornerRadius(w * 0.09f, w * 0.09f)
        )
        drawRoundRect(
            color = Color(0xFF6D972D),
            topLeft = Offset(w * 0.13f, h * 0.16f),
            size = Size(w * 0.74f, h * 0.14f),
            cornerRadius = CornerRadius(w * 0.08f, w * 0.08f)
        )
        drawRoundRect(
            color = Color(0xFF628826),
            topLeft = Offset(w * 0.26f, h * 0.10f),
            size = Size(w * 0.48f, h * 0.10f),
            cornerRadius = CornerRadius(w * 0.05f, w * 0.05f)
        )

        val recycle = Path().apply {
            moveTo(w * 0.44f, h * 0.44f)
            lineTo(w * 0.50f, h * 0.37f)
            lineTo(w * 0.56f, h * 0.44f)
            lineTo(w * 0.52f, h * 0.44f)
            lineTo(w * 0.58f, h * 0.52f)
            lineTo(w * 0.49f, h * 0.50f)
            lineTo(w * 0.52f, h * 0.47f)
            close()
            moveTo(w * 0.42f, h * 0.58f)
            lineTo(w * 0.35f, h * 0.52f)
            lineTo(w * 0.39f, h * 0.49f)
            lineTo(w * 0.31f, h * 0.47f)
            lineTo(w * 0.33f, h * 0.57f)
            lineTo(w * 0.37f, h * 0.54f)
            close()
            moveTo(w * 0.60f, h * 0.57f)
            lineTo(w * 0.63f, h * 0.47f)
            lineTo(w * 0.54f, h * 0.49f)
            lineTo(w * 0.58f, h * 0.52f)
            lineTo(w * 0.50f, h * 0.57f)
            lineTo(w * 0.56f, h * 0.60f)
            close()
        }
        drawPath(recycle, color = Color.White)
    }
}

private enum class ScrapType {
    Leaf,
    Peel,
    AppleCore,
    TeaBag,
    Banana,
    Cheese,
}

@Composable
private fun ScrapMotif(
    type: ScrapType,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        when (type) {
            ScrapType.Leaf -> drawLeaf()
            ScrapType.Peel -> drawPeel()
            ScrapType.AppleCore -> drawAppleCore()
            ScrapType.TeaBag -> drawTeaBag()
            ScrapType.Banana -> drawBanana()
            ScrapType.Cheese -> drawCheese()
        }
    }
}

@Composable
private fun SmallBerry(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(12.dp)) {
        drawCircle(Color(0xFFD45B32), radius = size.minDimension / 2)
        drawLine(
            color = Color(0xFF6A9A35),
            start = Offset(size.width * 0.5f, size.height * 0.12f),
            end = Offset(size.width * 0.72f, 0f),
            strokeWidth = size.minDimension * 0.12f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun SmallLeaf(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.size(16.dp)) {
        val leaf = Path().apply {
            moveTo(size.width * 0.12f, size.height * 0.58f)
            quadraticTo(size.width * 0.48f, size.height * 0.08f, size.width * 0.88f, size.height * 0.34f)
            quadraticTo(size.width * 0.58f, size.height * 0.98f, size.width * 0.12f, size.height * 0.58f)
        }
        drawPath(leaf, color = Color(0xFF7FB146))
    }
}

private fun DrawScope.drawLeaf() {
    val leaf = Path().apply {
        moveTo(size.width * 0.14f, size.height * 0.62f)
        quadraticTo(size.width * 0.50f, size.height * 0.10f, size.width * 0.88f, size.height * 0.38f)
        quadraticTo(size.width * 0.58f, size.height * 0.94f, size.width * 0.14f, size.height * 0.62f)
    }
    drawPath(leaf, color = Color(0xFF77A93E))
    drawLine(
        color = Color(0xFF5E8732),
        start = Offset(size.width * 0.22f, size.height * 0.60f),
        end = Offset(size.width * 0.72f, size.height * 0.34f),
        strokeWidth = size.minDimension * 0.05f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawPeel() {
    drawArc(
        color = Color(0xFFF39A2E),
        startAngle = 210f,
        sweepAngle = 120f,
        useCenter = false,
        topLeft = Offset(size.width * 0.10f, size.height * 0.18f),
        size = Size(size.width * 0.76f, size.height * 0.72f),
        style = Stroke(width = size.minDimension * 0.18f, cap = StrokeCap.Round)
    )
    drawLine(
        color = Color(0xFFF7C159),
        start = Offset(size.width * 0.36f, size.height * 0.54f),
        end = Offset(size.width * 0.56f, size.height * 0.26f),
        strokeWidth = size.minDimension * 0.08f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawAppleCore() {
    val body = Path().apply {
        moveTo(size.width * 0.36f, size.height * 0.20f)
        quadraticTo(size.width * 0.16f, size.height * 0.34f, size.width * 0.30f, size.height * 0.54f)
        quadraticTo(size.width * 0.42f, size.height * 0.70f, size.width * 0.36f, size.height * 0.84f)
        quadraticTo(size.width * 0.52f, size.height * 0.94f, size.width * 0.66f, size.height * 0.84f)
        quadraticTo(size.width * 0.60f, size.height * 0.70f, size.width * 0.72f, size.height * 0.54f)
        quadraticTo(size.width * 0.86f, size.height * 0.34f, size.width * 0.66f, size.height * 0.20f)
        close()
    }
    drawPath(body, color = Color(0xFFE8D3A4))
    drawLine(
        color = Color(0xFF7B5334),
        start = Offset(size.width * 0.5f, size.height * 0.04f),
        end = Offset(size.width * 0.5f, size.height * 0.16f),
        strokeWidth = size.minDimension * 0.05f,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawTeaBag() {
    drawRoundRect(
        color = Color(0xFFF6E6CF),
        topLeft = Offset(size.width * 0.24f, size.height * 0.20f),
        size = Size(size.width * 0.42f, size.height * 0.42f),
        cornerRadius = CornerRadius(size.minDimension * 0.05f, size.minDimension * 0.05f)
    )
    drawRect(
        color = Color(0xFF7A5639),
        topLeft = Offset(size.width * 0.24f, size.height * 0.42f),
        size = Size(size.width * 0.42f, size.height * 0.20f)
    )
    drawLine(
        color = Color(0xFFB89C7B),
        start = Offset(size.width * 0.46f, size.height * 0.20f),
        end = Offset(size.width * 0.74f, size.height * 0.06f),
        strokeWidth = size.minDimension * 0.04f
    )
    drawCircle(
        color = Color(0xFFF3E5C6),
        radius = size.minDimension * 0.08f,
        center = Offset(size.width * 0.80f, size.height * 0.06f),
        style = Fill
    )
}

private fun DrawScope.drawBanana() {
    val outer = Path().apply {
        moveTo(size.width * 0.16f, size.height * 0.68f)
        quadraticTo(size.width * 0.52f, size.height * 0.18f, size.width * 0.90f, size.height * 0.42f)
        quadraticTo(size.width * 0.54f, size.height * 0.56f, size.width * 0.20f, size.height * 0.84f)
        close()
    }
    val inner = Path().apply {
        moveTo(size.width * 0.28f, size.height * 0.66f)
        quadraticTo(size.width * 0.54f, size.height * 0.34f, size.width * 0.76f, size.height * 0.46f)
        quadraticTo(size.width * 0.52f, size.height * 0.54f, size.width * 0.30f, size.height * 0.74f)
        close()
    }
    drawPath(outer, color = Color(0xFFF2D34A))
    drawPath(inner, color = Color(0xFFF8E782))
}

private fun DrawScope.drawCheese() {
    val wedge = Path().apply {
        moveTo(size.width * 0.18f, size.height * 0.72f)
        lineTo(size.width * 0.82f, size.height * 0.72f)
        lineTo(size.width * 0.60f, size.height * 0.24f)
        close()
    }
    drawPath(wedge, color = Color(0xFFF2CA57))
    drawCircle(Color(0xFFE4B844), radius = size.minDimension * 0.07f, center = Offset(size.width * 0.42f, size.height * 0.56f))
    drawCircle(Color(0xFFE4B844), radius = size.minDimension * 0.05f, center = Offset(size.width * 0.60f, size.height * 0.62f))
    drawCircle(Color(0xFFE4B844), radius = size.minDimension * 0.04f, center = Offset(size.width * 0.56f, size.height * 0.42f))
}
