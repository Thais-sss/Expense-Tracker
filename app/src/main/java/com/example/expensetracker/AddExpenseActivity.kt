package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.ViewModel
import com.example.expensetracker.AddExpenseScreen
import com.example.expensetracker.YourViewModel

class AddExpenseActivity : ComponentActivity() { // extends ComponentActivity
    private val viewModel: YourViewModel by viewModels() // creating view model

    override fun onCreate(savedInstanceState: Bundle?) { // standard code
        super.onCreate(savedInstanceState)
        setContent { // setting content
            MaterialTheme {
                val currentScreen = viewModel.currentScreen.value

                when (currentScreen) { // a type of switch statement
                    is Screen.AddExpense -> { // if the currentScreen is addExpense, then display its view
                        AddExpenseScreen(viewModel = viewModel) { // passing viewModel as a parameter
                            viewModel.navigateTo(Screen.ViewExpenses) // navigating to another screen
                            finish() // finishing this current view
                        }
                    }
                    else -> { // if not, display viewExpenses
                        ViewExpenses(viewModel.expenses)
                    }
                }
            }
        }
    }
}
