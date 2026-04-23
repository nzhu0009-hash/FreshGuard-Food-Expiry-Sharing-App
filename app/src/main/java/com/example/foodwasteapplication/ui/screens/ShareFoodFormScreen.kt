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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.ArrowDropDown

private const val MIN_FOOD_NAME_LENGTH = 2
private const val MAX_FOOD_NAME_LENGTH = 30
private const val MIN_QUANTITY_LENGTH = 1
private const val MAX_QUANTITY_LENGTH = 20

private fun validateShareFoodInput(
    foodName: String,
    quantity: String
): String? {
    val errors = mutableListOf<String>()
    val trimmedName = foodName.trim()
    val trimmedQuantity = quantity.trim()

    if (trimmedName.isBlank()) {
        errors.add("Food name is required.")
    } else {
        if (trimmedName.length < MIN_FOOD_NAME_LENGTH) {
            errors.add("Food name must be at least $MIN_FOOD_NAME_LENGTH characters.")
        }
        if (trimmedName.length > MAX_FOOD_NAME_LENGTH) {
            errors.add("Food name must be no more than $MAX_FOOD_NAME_LENGTH characters.")
        }
    }

    if (trimmedQuantity.isBlank()) {
        errors.add("Quantity is required.")
    } else {
        if (trimmedQuantity.length < MIN_QUANTITY_LENGTH) {
            errors.add("Quantity must be at least $MIN_QUANTITY_LENGTH character.")
        }
        if (trimmedQuantity.length > MAX_QUANTITY_LENGTH) {
            errors.add("Quantity must be no more than $MAX_QUANTITY_LENGTH characters.")
        }
    }

    return if (errors.isEmpty()) null else errors.joinToString("\n")
}

@Composable
fun ShareFoodFormScreen(
    topPadding: PaddingValues,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Fruit") }
    var pickupLocation by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var warningMessage by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Fruit", "Vegetable", "Dairy", "Bakery", "Cooked Food")
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                            expiryDate = formatter.format(Date(selectedMillis))
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
            DatePicker(state = datePickerState)
        }
    }

    warningMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { warningMessage = null },
            title = { Text("Invalid Input") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { warningMessage = null }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(topPadding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Share Food Form",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Publish surplus food for community sharing.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = foodName,
            onValueChange = { foodName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Food name") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Quantity") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Category") },
                trailingIcon = {
                    TextButton(onClick = { expanded = true }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Pick")
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Open category menu"
                            )
                        }
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = expiryDate,
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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pickupLocation,
            onValueChange = { pickupLocation = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Pickup location") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    val error = validateShareFoodInput(
                        foodName = foodName,
                        quantity = quantity
                    )

                    if (error != null) {
                        warningMessage = error
                    } else {
                        onSubmit()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}