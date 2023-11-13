package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewExpensesScreen(expenses: List<Expense>,
                       navigateToAddExpense: () -> Unit
) {
    // im not currently using them
    var totalDaily by remember { mutableDoubleStateOf(0.0) }
    var totalWeekly by remember { mutableDoubleStateOf(0.0) }
    var totalMonthly by remember { mutableDoubleStateOf(0.0) }

    // Functions to calculate totals
    // For example:
    // totalDaily = calculateDailyTotal(expenses)
    // totalWeekly = calculateWeeklyTotal(expenses)
    // totalMonthly = calculateMonthlyTotal(expenses)

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
            }
        }
    }
}
// i have not really implemented this yet
// i thought i was going to but it got complicated
// my code started to break so i decided to wait and see
// if i will have the time or not
// i might just get rid of this block below if i see that
// i will not have enough time
@RequiresApi(Build.VERSION_CODES.O)
fun calculateDailyTotal(expenses: List<Expense>): Double {
    val today = LocalDate.now()
    return expenses.filter { it.date == today }.sumByDouble { it.amount.toDouble() }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateWeeklyTotal(expenses: List<Expense>): Double {
    val today = LocalDate.now()
    val weekStart = today.minusDays(today.dayOfWeek.value.toLong() - 1)
    val weekEnd = weekStart.plusDays(6)
    return expenses.filter { it.date in weekStart..weekEnd }.sumByDouble { it.amount.toDouble() }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateMonthlyTotal(expenses: List<Expense>): Double {
    val today = LocalDate.now()
    val monthStart = LocalDate.of(today.year, today.month, 1)
    val monthEnd = monthStart.plusMonths(1).minusDays(1)
    return expenses.filter { it.date in monthStart..monthEnd }.sumByDouble { it.amount.toDouble() }
}