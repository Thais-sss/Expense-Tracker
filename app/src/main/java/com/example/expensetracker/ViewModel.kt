package com.example.expensetracker

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

sealed class Screen(val route: String) {
    // sealed class represents a hierarchy
    object AddExpense : Screen("add_expense")
    object ViewExpenses : Screen("view_expenses")
}

class YourViewModel : ViewModel() { // viewModel inheritance
    val expenses = mutableStateListOf<Expense>() // whenever it gets changed, it will trigger recomposition
    val currentScreen = mutableStateOf<Screen>(Screen.AddExpense) // same for currentScreen

    fun navigateTo(screen: Screen) { // method to navigate through screens
        currentScreen.value = screen // by getting the value for the currentScreen.
    }

    fun addExpense(expense: Expense) { // method to add a new expense
        expenses.add(expense)
    }

    fun startLocationService(context: Context) {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
        }
    }
}