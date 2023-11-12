package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.ViewExpensesScreen


class MainActivity : ComponentActivity() {
    private val viewModel: YourViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "welcome_route") {
                composable("welcome_route") {
                    WelcomeScreen(
                        onAddExpensesClick = {
                            viewModel.navigateTo(Screen.AddExpense)
                            navController.navigate(Screen.AddExpense.route)
                        },
                        onViewExpensesClick = {
                            viewModel.navigateTo(Screen.ViewExpenses)
                            navController.navigate(Screen.ViewExpenses.route)
                        }
                    )
                }
                composable(Screen.AddExpense.route) {
                    ExpenseTrackerApp(viewModel = viewModel, navController = navController)
                }
                composable(Screen.ViewExpenses.route) {
                    ViewExpensesScreen(
                        expenses = viewModel.expenses,
                        navigateToAddExpense = {
                            viewModel.navigateTo(Screen.AddExpense)
                            navController.navigate(Screen.AddExpense.route)
                        }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseTrackerApp(viewModel: YourViewModel, navController: NavController) {
    val currentScreen by viewModel.currentScreen
    val expenses = viewModel.expenses.toList()

    when (currentScreen) {
        Screen.AddExpense -> {
            var newExpense by remember { mutableStateOf(Expense("", "")) }

            AddExpenseScreen(
                viewModel = viewModel,
                navigateToViewExpenses = {
                    viewModel.addExpense(newExpense)
                    viewModel.navigateTo(Screen.ViewExpenses)
                    navController.navigate(Screen.ViewExpenses.route)
                }
            )
        }
        Screen.ViewExpenses -> {
            ViewExpensesScreen(expenses = expenses,
                navigateToAddExpense = {
                    viewModel.navigateTo(Screen.AddExpense)
                    navController.navigate(Screen.AddExpense.route)
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ExpenseTrackerAppPreview() {
    ExpenseTrackerApp(viewModel = YourViewModel(), navController = rememberNavController())
}