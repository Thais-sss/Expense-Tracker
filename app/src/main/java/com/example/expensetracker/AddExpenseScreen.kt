package com.example.expensetracker

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseScreen(viewModel: YourViewModel, navigateToViewExpenses: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Expense",
            modifier = Modifier.padding(16.dp)
        )

        val expenseNameState = remember { mutableStateOf("")}
        val amountState = remember { mutableStateOf("")}

        // Text fields for expense details
        TextField(
            value = expenseNameState.value, // Use state to manage input
            onValueChange = { expenseNameState.value = it },
            label = { Text("Expense Name") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        TextField(
            value = amountState.value, // Use state to manage input
            onValueChange = { amountState.value = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        var showError by remember { mutableStateOf(false) }
        // Button to add the expense
        // Submit Button
        Button(
            onClick = {
                // Save the entered expense to a list, database, or ViewModel
                val newExpense = Expense(expenseNameState.value, amountState.value)
                // Call a function to add the expense to the list or database
                // addExpense(newExpense)

                val name = expenseNameState.value
                val amount = amountState.value

                if (name.isNotBlank() && amount.isNotBlank()) {
                    val newExpense = Expense(name, amount)
                    viewModel.addExpense(newExpense)
                    navigateToViewExpenses()

                    // Clear the text fields after submission
                    expenseNameState.value = ""
                    amountState.value = ""
                } else {
                    showError = true
                }
            },
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("Submit", color = Color.White)
        }
        if (showError) {
            Text("Please enter both name and amount", color = Color.Red)
        }
        // Implement button functionality here
    }
}

@Composable
fun ViewExpenses(expenses: List<Expense>) {
    // Display the list of expenses in a suitable format (e.g., in a LazyColumn)
    LazyColumn {
        items(expenses) { expense ->
            Text("Name: ${expense.name}, Amount: ${expense.amount}")
        }
    }
}

//@Preview
//@Composable
//fun PreviewAddExpenseScreen() {
//    AddExpenseScreen()
//}

