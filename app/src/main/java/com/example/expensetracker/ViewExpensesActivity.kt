package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme

class ViewExpensesActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val expensesList = listOf<Expense>() // Fetch or create your expenses list

        setContent {
            MaterialTheme {
                ViewExpensesScreen(expenses = expensesList, navigateToAddExpense = { })
            }
        }
    }
}