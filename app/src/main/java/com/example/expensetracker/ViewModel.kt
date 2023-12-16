// specifying package name
package com.example.expensetracker

// importing necessary libraries, compose, coroutines and so on
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore

// declaring a class called Expense
// with two properties: name and amount
data class Expense(
    val name: String,
    val amount: String
) {
    // Default no-argument constructor required by Firebase
    constructor() : this("", "")
}
// declaring sealed class
sealed class Screen(val route: String) {
    // sealed class represents a hierarchy
    // so these are subclasses: addExpense and viewExpenses
    // passing their route
    object AddExpense : Screen("add_expense")
    object ViewExpenses : Screen("view_expenses")
}

// declaring ViewModel class which is going to be widely used
// in this application and it will manage the data and logic for the Compose UI
class YourViewModel : ViewModel() { // viewModel inheritance
    // declaring a firestore instance
    private val firestore = FirebaseFirestore.getInstance()
    // adding a collection reference
    private val expensesCollection = firestore.collection("expenses")

    // using a mutable state list for the expenses and current screen
    val expenses = mutableStateListOf<Expense>() // whenever it gets changed, it will trigger recomposition
    val currentScreen = mutableStateOf<Screen>(Screen.AddExpense) // same for currentScreen

    // following the singleton pattern
    companion object {
        val instance by lazy { YourViewModel() }
    }

    // this initializes the ViewModel
    init {
        // Load expenses from Firestore when ViewModel is created
        loadExpenses()
    }

    // method to navigate to different screens
    fun navigateTo(screen: Screen) {
        currentScreen.value = screen // by getting the value for the currentScreen.
    }


    fun addExpense(expense: Expense) { // method to add a new expense
        viewModelScope.launch {
            expenses.add(expense) // take the expenses already saved and add a new one to it
            saveExpenseToFirestore(expense) // calling the function that saves it to firestore
        }    }

    // this will use an intent to start the location service
    fun startLocationService(context: Context) {
        // starting location service
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            // calling the start from location service class
            context.startService(this)
        }
    }

    // deleting an expense
    // create a function for that
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            // it deletes local and then it deletes it on firestore
            expenses.remove(expense)
            // deleting expenses on firestore
            deleteExpenseFromFirestore(expense)
        }
    }

    // function to save expenses to firestore
    // passing expense as a parameter
    private fun saveExpenseToFirestore(expense: Expense) {
        // saving expenses to firestore
        expensesCollection.add(expense)
    }


    // function to save expenses locally
    private fun saveExpense(expense: Expense) {
        // calling the method add and passing expense as a parameter
        expensesCollection.add(expense)
    }

    // function to delete expenses
    // passing expense as a parameter
    private fun deleteExpenseFromFirestore(expense: Expense) {
        // taking each expense ID
        val expenseId = getExpenseId(expense)
        // deleting it by using the expense ID
        expensesCollection.document(expenseId).delete() // delete from firestore collection
    }

    private fun loadExpenses() { // function to load expenses
        // using a snapshot listener
        expensesCollection.addSnapshotListener { querySnapshot, _ ->
            querySnapshot?.let {
                expenses.clear()
                for (document in it) { // foor loop to display all expenses
                    val expense = document.toObject(Expense::class.java)
                    expenses.add(expense) // adding expenses
                }
            }
        }
    }

    private fun getExpenseId(expense: Expense): String {
        // i found the structure of this function to implement it
        // but i ended up not implementing it
        // this was when i was trying to get the expenses to be deleted from firebase
        // they are being deleted locally fine
        return "${expense.name}_${expense.amount}"
    }
}