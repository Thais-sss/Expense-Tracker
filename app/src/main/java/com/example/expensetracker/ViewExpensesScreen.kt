package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewExpensesScreen(expenses: List<Expense>,
                       navigateToAddExpense: () -> Unit
) {

    var totalExpense by remember { mutableDoubleStateOf(0.0) }

    totalExpense = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }
        .sumByDouble { it.amount.toDouble() }

    // check that they are not blank
    val validExpenses = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }

    Button(
        // applying desing and logic
        // design with modifier
        // logic with navigateTo depeding on the click
        onClick = { navigateToAddExpense() },
        modifier = Modifier
            .padding(8.dp)
            .background(Color(0xFF1E1E1E)) // Dark background color
                 ) {
        Text("Add Expense")
    }

    // check if there are expenses already or not
    if (validExpenses.isEmpty()) {
        Column(
            // applying design
            modifier = Modifier.fillMaxSize()
                .background(Color(0xFF1E1E1E)), // Dark background color
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // in case there are no expenses in yet
            Text("No expenses added yet", modifier = Modifier.padding(16.dp))
        }

    } else {

        // otherwise, display them
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(validExpenses) { expense ->
                    Text(
                        "Name: ${expense.name}, Amount: ${expense.amount}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                item {
                    Text("Total Expense: $totalExpense", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ViewExpensesScreenPreview() {
    // Sample list of expenses for preview
    val sampleExpenses = listOf(
        Expense("Groceries", "50.0"),
        Expense("Utilities", "100.0"),
        Expense("Dinner", "30.0")
    )

    // Function to navigate to add expense (not used in preview)
    val navigateToAddExpense: () -> Unit = {}

    // Previewing the ViewExpensesScreen with sample data
    ViewExpensesScreen(expenses = sampleExpenses, navigateToAddExpense = navigateToAddExpense)
}
