package com.example.foodwasteapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodwasteapplication.ui.theme.SageGreen
import com.example.foodwasteapplication.ui.theme.SoftCoral

data class SharedFoodItem(
    val name: String,
    val quantity: String,
    val expiry: String,
    val pickupLocation: String,
    val availability: String,
    val daysRemaining: Int
)

private fun expiryChipText(daysRemaining: Int): String {
    return when {
        daysRemaining < 0 -> "Expired ${-daysRemaining} day(s) ago"
        daysRemaining == 0 -> "Expires today"
        daysRemaining == 1 -> "1 day left"
        daysRemaining <= 2 -> "Expiring soon"
        else -> "Fresh"
    }
}

@Composable
private fun chipContainerColor(daysRemaining: Int) = when {
    daysRemaining < 0 -> SoftCoral.copy(alpha = 0.24f)
    daysRemaining == 0 -> SoftCoral.copy(alpha = 0.18f)
    daysRemaining <= 2 -> SoftCoral.copy(alpha = 0.10f)
    else -> SageGreen.copy(alpha = 0.35f)
}

@Composable
private fun chipLabelColor(daysRemaining: Int) = when {
    daysRemaining < 0 -> MaterialTheme.colorScheme.tertiary
    daysRemaining == 0 -> MaterialTheme.colorScheme.tertiary
    daysRemaining <= 2 -> MaterialTheme.colorScheme.onSurface
    else -> MaterialTheme.colorScheme.onSurface
}

@Composable
fun FoodSharingScreen(
    topPadding: PaddingValues,
    onNavigateToShareForm: () -> Unit
) {
    val sharedFoods = remember {
        listOf(
            SharedFoodItem(
                name = "Bread Loaf",
                quantity = "2 packs",
                expiry = "Tomorrow",
                pickupLocation = "Clayton",
                availability = "Available",
                daysRemaining = 1
            ),
            SharedFoodItem(
                name = "Apples",
                quantity = "5 items",
                expiry = "In 2 days",
                pickupLocation = "Box Hill",
                availability = "Available",
                daysRemaining = 2
            ),
            SharedFoodItem(
                name = "Milk",
                quantity = "1 bottle",
                expiry = "Today",
                pickupLocation = "Glen Waverley",
                availability = "Urgent",
                daysRemaining = 0
            ),
            SharedFoodItem(
                name = "Cooked Rice",
                quantity = "1 container",
                expiry = "Yesterday",
                pickupLocation = "CBD",
                availability = "Available",
                daysRemaining = -1
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }

    val filteredFoods = sharedFoods.filter { food ->
        val query = searchQuery.trim()
        query.isBlank() ||
                food.name.contains(query, ignoreCase = true) ||
                food.pickupLocation.contains(query, ignoreCase = true) ||
                food.expiry.contains(query, ignoreCase = true) ||
                food.availability.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topPadding)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Food Sharing",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Button(onClick = onNavigateToShareForm) {
                Text("Share your Food")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Share surplus food with the community and reduce waste.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search shared food") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredFoods.isEmpty()) {
            Text(
                text = "No matching shared food found.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(filteredFoods) { food ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Text(
                                    text = food.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = "Quantity: ${food.quantity}   Pickup: ${food.pickupLocation}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Text(
                                    text = "Expiry date: ${food.expiry}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Button(onClick = {}) {
                                Text("Request")
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AssistChip(
                                onClick = {},
                                enabled = true,
                                colors = AssistChipDefaults.assistChipColors(
                                    labelColor = chipLabelColor(food.daysRemaining),
                                    containerColor = chipContainerColor(food.daysRemaining)
                                ),
                                label = {
                                    Text(
                                        text = expiryChipText(food.daysRemaining),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            )

                            Text(
                                text = food.availability,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}