package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
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
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Add Expense")
    }

    // check if there are expenses already or not
    if (validExpenses.isEmpty()) {
        Column(
            // applying design
            modifier = Modifier.fillMaxSize(),
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