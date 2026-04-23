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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.foodwasteapplication.auth.MockAuthStore

@Composable
fun HomeEntryScreen(
    topPadding: PaddingValues = PaddingValues(),
    onOpenProfile: () -> Unit,
    onNavigateToRoute: (String) -> Unit,
) {
    val currentUser = MockAuthStore.currentUser
    val isDemoAccount = currentUser?.email.equals("user@test.com", ignoreCase = true)
    val welcomeText = if (currentUser != null && !isDemoAccount && currentUser.name.isNotBlank()) {
        "Welcome, ${currentUser.name}. Track expiry, share food, and view waste insights."
    } else {
        "Welcome. Track expiry, share food, and view waste insights."
    }

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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                        text = welcomeText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(30.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.72f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FreshGuardAvatar(
                        modifier = Modifier.size(64.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Home overview",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Manage food expiry, sharing, reminders, and statistics from one connected app flow.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = onOpenProfile,
                            modifier = Modifier.padding(start = 0.dp)
                        ) {
                            Text("Open My Profile")
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
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Next module handoff",
                        style = MaterialTheme.typography.titleLarge
                    )
                    NavigationActionCard(
                        title = "Food List",
                        subtitle = "Manage food inventory with search, filters and CRUD features.",
                        buttonLabel = "Open Food List",
                        onClick = { onNavigateToRoute("add_food") }
                    )
                    NavigationActionCard(
                        title = "Share",
                        subtitle = "Navigate to the sharing module for community food exchange.",
                        buttonLabel = "Open Share",
                        onClick = { onNavigateToRoute("share") }
                    )
                    NavigationActionCard(
                        title = "Statistics",
                        subtitle = "Open analytics and waste insights screens for visual summaries.",
                        buttonLabel = "Open Statistics",
                        onClick = { onNavigateToRoute("stats") }
                    )
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
                        text = "Home flow ready",
                        style = MaterialTheme.typography.titleLarge
                    )
                    ScopeRow("Users can register a local mock account")
                    ScopeRow("New account email is returned to Login")
                    ScopeRow("Bottom bar routes switch between team modules")
                    ScopeRow("Profile screen is reachable from Home")
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
private fun NavigationActionCard(
    title: String,
    subtitle: String,
    buttonLabel: String,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(onClick = onClick) {
                Text(buttonLabel)
            }
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
