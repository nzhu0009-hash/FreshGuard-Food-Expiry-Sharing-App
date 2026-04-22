package com.example.foodwasteapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.foodwasteapplication.food.FoodViewModel
import com.example.foodwasteapplication.food.data.FoodItemEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

const val FOOD_EDITOR_BASE_ROUTE = "food_editor"
const val FOOD_EDITOR_ARG_ID = "foodId"
const val NEW_FOOD_ID = -1L
const val FOOD_EDITOR_ROUTE = "$FOOD_EDITOR_BASE_ROUTE/{$FOOD_EDITOR_ARG_ID}"

fun buildFoodEditorRoute(foodId: Long? = null): String {
    return "$FOOD_EDITOR_BASE_ROUTE/${foodId ?: NEW_FOOD_ID}"
}

@Composable
fun FoodListScreen(
    topPadding: PaddingValues,
    foods: List<FoodItemEntity>,
    onAddFood: () -> Unit,
    onEditFood: (Long) -> Unit,
    onDeleteFood: (FoodItemEntity) -> Unit,
) {
    var pendingDeleteFood by rememberSaveable { mutableStateOf<Long?>(null) }
    val foodToDelete = foods.firstOrNull { it.id == pendingDeleteFood }

    if (foodToDelete != null) {
        AlertDialog(
            onDismissRequest = { pendingDeleteFood = null },
            title = { Text("Delete Food?") },
            text = {
                Text("Are you sure you want to delete \"${foodToDelete.name}\"?")
            },
            confirmButton = {
                TextButton(onClick = { pendingDeleteFood = null }) {
                    Text("Cancel")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDeleteFood(foodToDelete)
                        pendingDeleteFood = null
                    }
                ) {
                    Text("Confirm")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topPadding)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Food List",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Add, edit and manage food items before they expire.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Button(onClick = onAddFood) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add food"
                )
                Text(
                    text = "Add",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }

        if (foods.isEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "No food items yet. Tap Add to create your first entry.",
                    modifier = Modifier.padding(18.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            return
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = foods,
                key = { it.id }
            ) { food ->
                FoodCard(
                    food = food,
                    onEditFood = { onEditFood(food.id) },
                    onDeleteFood = { pendingDeleteFood = food.id }
                )
            }
        }
    }
}

@Composable
private fun FoodCard(
    food: FoodItemEntity,
    onEditFood: () -> Unit,
    onDeleteFood: () -> Unit,
) {
    val daysRemaining = daysUntil(food.expiryDateMillis)
    val statusText = when {
        daysRemaining < 0 -> "Expired ${-daysRemaining} day(s) ago"
        daysRemaining == 0 -> "Expires today"
        daysRemaining <= 2 -> "Expiring soon"
        else -> "Fresh"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                        text = food.name.replace(Regex("\\s*\\(.*?\\)"), "").trim(),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Quantity: ${food.quantity}, Category: ${food.category}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Expiry date: ${formatDate(food.expiryDateMillis)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onEditFood) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit"
                    )
                }
                IconButton(onClick = onDeleteFood) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete"
                    )
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
                        labelColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    label = {
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                if (food.note.isNotBlank()) {
                    Text(
                        text = food.note,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodEditorScreen(
    topPadding: PaddingValues,
    foodId: Long,
    viewModel: FoodViewModel,
    onSavedOrClosed: () -> Unit,
) {
    val isEditing = foodId != NEW_FOOD_ID
    val categories = listOf("General", "Vegetable", "Fruit", "Dairy", "Meat", "Drink", "Other")
    var name by rememberSaveable(foodId) { mutableStateOf("") }
    var quantityText by rememberSaveable(foodId) { mutableStateOf("1") }
    var expiryDateMillis by rememberSaveable(foodId) { mutableStateOf(defaultExpiryDate()) }
    var category by rememberSaveable(foodId) { mutableStateOf(categories.first()) }
    var note by rememberSaveable(foodId) { mutableStateOf("") }
    var createdAtMillis by rememberSaveable(foodId) { mutableStateOf(System.currentTimeMillis()) }
    var errorText by rememberSaveable(foodId) { mutableStateOf("") }
    var categoryExpanded by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(foodId) {
        if (!isEditing) return@LaunchedEffect
        val existingFood = viewModel.findFood(foodId) ?: return@LaunchedEffect
        name = existingFood.name
        quantityText = existingFood.quantity.toString()
        expiryDateMillis = existingFood.expiryDateMillis
        category = existingFood.category
        note = existingFood.note
        createdAtMillis = existingFood.createdAtMillis
    }

    if (showDatePicker) {
        val pickerState = rememberDatePickerState(initialSelectedDateMillis = expiryDateMillis)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val picked = pickerState.selectedDateMillis
                        if (picked != null) {
                            expiryDateMillis = normalizeDate(picked)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topPadding)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = if (isEditing) "Edit Food" else "Add Food",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Create and maintain your food inventory for expiry tracking.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                errorText = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Food name") },
            singleLine = true
        )

        OutlinedTextField(
            value = quantityText,
            onValueChange = {
                quantityText = it
                errorText = ""
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Column {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                trailingIcon = { TextButton(onClick = { categoryExpanded = true }) { Text("Pick") } },
                modifier = Modifier
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                categories.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            category = option
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = formatDate(expiryDateMillis),
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Expiry date") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Select expiry date"
                    )
                }
            }
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            label = { Text("Note (optional)") }
        )

        if (errorText.isNotBlank()) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    val quantity = quantityText.toIntOrNull()
                    if (name.isBlank()) {
                        errorText = "Food name is required"
                        return@Button
                    }
                    if (quantity == null || quantity <= 0) {
                        errorText = "Quantity must be a positive number"
                        return@Button
                    }

                    viewModel.saveFood(
                        food = FoodItemEntity(
                            id = if (isEditing) foodId else 0,
                            name = name.trim(),
                            quantity = quantity,
                            expiryDateMillis = normalizeDate(expiryDateMillis),
                            category = category,
                            note = note.trim(),
                            createdAtMillis = createdAtMillis
                        ),
                        isEditing = isEditing
                    )
                    onSavedOrClosed()
                }
            ) {
                Text(if (isEditing) "Save Changes" else "Save Food")
            }
            TextButton(onClick = onSavedOrClosed) {
                Text("Cancel")
            }
        }
    }
}

private fun formatDate(timestampMillis: Long): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(timestampMillis))
}

private fun daysUntil(expiryDateMillis: Long): Int {
    val today = startOfToday()
    val normalizedExpiry = normalizeDate(expiryDateMillis)
    return TimeUnit.MILLISECONDS.toDays(normalizedExpiry - today).toInt()
}

private fun defaultExpiryDate(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 2)
    return normalizeDate(calendar.timeInMillis)
}

private fun startOfToday(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

private fun normalizeDate(timestampMillis: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestampMillis
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}
