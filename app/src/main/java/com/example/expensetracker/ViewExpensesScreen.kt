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
    var totalDaily by remember { mutableDoubleStateOf(0.0) }
    var totalWeekly by remember { mutableDoubleStateOf(0.0) }
    var totalMonthly by remember { mutableDoubleStateOf(0.0) }

    // Functions to calculate totals
    // For example:
    // totalDaily = calculateDailyTotal(expenses)
    // totalWeekly = calculateWeeklyTotal(expenses)
    // totalMonthly = calculateMonthlyTotal(expenses)

    val validExpenses = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }

    Button(
        onClick = { navigateToAddExpense() },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Add Expense")
    }

    if (validExpenses.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No expenses added yet", modifier = Modifier.padding(16.dp))
        }

    } else {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//        Text("Daily Total: $totalDaily", modifier = Modifier.padding(8.dp))
//        Text("Weekly Total: $totalWeekly", modifier = Modifier.padding(8.dp))
//        Text("Monthly Total: $totalMonthly", modifier = Modifier.padding(8.dp))

//        // Add buttons to recalculate the totals
//        Button(
//            onClick = {
//                totalDaily = calculateDailyTotal(expenses)
//            },
//            modifier = Modifier.padding(8.dp),
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
//        ) {
//            Text("Daily Total", color = Color.White)
//        }

//        Button(
//            onClick = {
//                totalWeekly = calculateWeeklyTotal(expenses)
//            },
//            modifier = Modifier.padding(8.dp),
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
//        ) {
//            Text("Weekly Total", color = Color.White)
//        }

//        Button(
//            onClick = {
//                totalMonthly = calculateMonthlyTotal(expenses)
//            },
//            modifier = Modifier.padding(8.dp),
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
//        ) {
//            Text("Monthly Total", color = Color.White)
//        }

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