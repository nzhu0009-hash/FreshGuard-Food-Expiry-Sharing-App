package com.example.foodwasteapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeEntryScreen(
    topPadding: PaddingValues = PaddingValues(),
    onOpenProfile: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(topPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "FreshGuard",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Authentication and navigation prototype for the team project.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = onOpenProfile) {
                    Text("My Profile")
                }
            }

            Surface(
                shape = RoundedCornerShape(30.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.72f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FreshGuardAvatar(
                        modifier = Modifier.size(78.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Profile and navigation entry",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Open the profile page to preview account info, drawer navigation and user details.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(
                            onClick = onOpenProfile,
                            modifier = Modifier.padding(start = 0.dp)
                        ) {
                            Text("Open Profile")
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Member 1 scope",
                        style = MaterialTheme.typography.titleLarge
                    )
                    ScopeRow("Login and register flow in Jetpack Compose")
                    ScopeRow("Navigation shell with bottom bar and screen routing")
                    ScopeRow("User profile page and menu-based navigation")
                    ScopeRow("Reusable layout structure for teammates to plug into")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Navigation preview",
                        style = MaterialTheme.typography.titleLarge
                    )
                    NavigationHintCard("Home", "Landing page and profile shortcut")
                    NavigationHintCard("Add Food", "Reserved for Member 2 CRUD implementation")
                    NavigationHintCard("Share", "Reserved for Member 4 Firebase sharing flow")
                    NavigationHintCard("Stats", "Reserved for Member 4 analytics and charts")
                }
            }
        }
    }
}

@Composable
fun ModulePlaceholderScreen(
    topPadding: PaddingValues = PaddingValues(),
    title: String,
    owner: String,
    description: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(topPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(22.dp))
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "$owner module placeholder",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun ScopeRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun NavigationHintCard(
    title: String,
    subtitle: String,
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun FreshGuardAvatar(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        drawCircle(
            color = Color(0xFFD9D3FF),
            radius = size.minDimension / 2
        )
        drawArc(
            color = Color(0xFFF3C33E),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(size.width * 0.20f, size.height * 0.34f),
            size = Size(size.width * 0.60f, size.height * 0.50f)
        )
        drawCircle(
            color = Color.White,
            radius = size.minDimension * 0.075f,
            center = Offset(size.width * 0.40f, size.height * 0.58f)
        )
        drawCircle(
            color = Color.White,
            radius = size.minDimension * 0.075f,
            center = Offset(size.width * 0.60f, size.height * 0.58f)
        )
        drawCircle(
            color = Color(0xFF5F4A2A),
            radius = size.minDimension * 0.028f,
            center = Offset(size.width * 0.40f, size.height * 0.58f)
        )
        drawCircle(
            color = Color(0xFF5F4A2A),
            radius = size.minDimension * 0.028f,
            center = Offset(size.width * 0.60f, size.height * 0.58f)
        )
        drawArc(
            color = Color(0xFF5F4A2A),
            startAngle = 20f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(size.width * 0.40f, size.height * 0.62f),
            size = Size(size.width * 0.20f, size.height * 0.14f)
        )
        drawArc(
            color = Color(0xFF00A8A8),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(size.width * 0.28f, size.height * 0.14f),
            size = Size(size.width * 0.34f, size.height * 0.26f)
        )
        drawArc(
            color = Color(0xFF00A8A8),
            startAngle = 240f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(size.width * 0.46f, size.height * 0.04f),
            size = Size(size.width * 0.22f, size.height * 0.20f)
        )
    }
}
