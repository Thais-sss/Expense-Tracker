package com.example.expensetracker

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore

data class Expense(
    val name: String,
    val amount: String
) {
    // Default no-argument constructor required by Firebase
    constructor() : this("", "")
}
sealed class Screen(val route: String) {
    // sealed class represents a hierarchy
    object AddExpense : Screen("add_expense")
    object ViewExpenses : Screen("view_expenses")
}

class YourViewModel : ViewModel() { // viewModel inheritance
    private val firestore = FirebaseFirestore.getInstance()
    private val expensesCollection = firestore.collection("expenses")

    val expenses = mutableStateListOf<Expense>() // whenever it gets changed, it will trigger recomposition
    val currentScreen = mutableStateOf<Screen>(Screen.AddExpense) // same for currentScreen

    companion object {
        val instance by lazy { YourViewModel() }
    }

    init {
        // Load expenses from Firestore when ViewModel is created
        loadExpenses()
    }

    fun navigateTo(screen: Screen) { // method to navigate through screens
        currentScreen.value = screen // by getting the value for the currentScreen.
    }

    fun addExpense(expense: Expense) { // method to add a new expense
        viewModelScope.launch {
            expenses.add(expense)
            saveExpenseToFirestore(expense)
        }    }

    fun startLocationService(context: Context) {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenses.remove(expense)
            deleteExpenseFromFirestore(expense)
        }
    }

    private fun saveExpenseToFirestore(expense: Expense) {
        expensesCollection.add(expense)
    }


    private fun saveExpense(expense: Expense) {
        expensesCollection.add(expense)
    }

    private fun deleteExpenseFromFirestore(expense: Expense) {
        // Assuming you have some unique identifier for each expense
        val expenseId = getExpenseId(expense)
        expensesCollection.document(expenseId).delete()
    }

    private fun loadExpenses() {
        expensesCollection.addSnapshotListener { querySnapshot, _ ->
            querySnapshot?.let {
                expenses.clear()
                for (document in it) {
                    val expense = document.toObject(Expense::class.java)
                    expenses.add(expense)
                }
            }
        }
    }

    private fun getExpenseId(expense: Expense): String {
        // You need to implement logic to generate a unique ID for each expense
        // For simplicity, you can use some properties of the expense to create an ID
        return "${expense.name}_${expense.amount}"
    }
}