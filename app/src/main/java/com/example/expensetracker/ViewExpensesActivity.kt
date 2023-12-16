// package name
package com.example.expensetracker

// necessary import
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme

// declaring class view expenses activity
// this class extends component activity
class ViewExpensesActivity : ComponentActivity() {
    // requires api was a necessary import
    @RequiresApi(Build.VERSION_CODES.O)
    // override function on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // creating expense list to store the list of expenses
        val expensesList = listOf<Expense>()

        // setting the content
        setContent {
            // using material theme
            MaterialTheme {
                // Display viewExpensesScreen
                ViewExpensesScreen(
                    // this will display the ui
                    // with the expense list
                    expenses = expensesList,
                    navigateToAddExpense = { },
                    // delete expense will handle the delete action
                    onDeleteClick = YourViewModel.instance::deleteExpense
                )
            }
        }
    }
}
