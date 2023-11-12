package com.example.expensetracker

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

sealed class Screen(val route: String) {
    object AddExpense : Screen("add_expense")
    object ViewExpenses : Screen("view_expenses")
}

class YourViewModel : ViewModel() {
    val expenses = mutableStateListOf<Expense>()
    val currentScreen = mutableStateOf<Screen>(Screen.AddExpense)

    fun navigateTo(screen: Screen) {
        currentScreen.value = screen
    }

    fun addExpense(expense: Expense) {
        expenses.add(expense)
    }
}