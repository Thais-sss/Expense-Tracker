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
    // setting viewModel
    private val viewModel: YourViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // setting content
            // creating instance of navController
            val navController = rememberNavController()

            // setting the landing screen to be the welcome screen
            NavHost(navController = navController, startDestination = "welcome_route") {
                // composing welcome screen
                composable("welcome_route") {
                    WelcomeScreen(
                        // if the user clicks on addExpense button
                        // navigates them to addExpenseScreen
                        onAddExpensesClick = {
                            viewModel.navigateTo(Screen.AddExpense)
                            navController.navigate(Screen.AddExpense.route)
                        },
                        // if the user clicks on viewExpenses button
                        // navigates them to viewExpensesScreen
                        onViewExpensesClick = {
                            viewModel.navigateTo(Screen.ViewExpenses)
                            navController.navigate(Screen.ViewExpenses.route)
                        }
                    )
                }
                // composing addExpense
                composable(Screen.AddExpense.route) {
                    ExpenseTrackerApp(viewModel = viewModel, navController = navController)
                }
                // composing viewExpenses
                composable(Screen.ViewExpenses.route) {
                    ViewExpensesScreen(
                        expenses = viewModel.expenses,
                        navigateToAddExpense = {
                            viewModel.navigateTo(Screen.AddExpense)
                            // defining which screen to navigate to
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
    // calling method above and passing parameters
    // declaring variables below
    val currentScreen by viewModel.currentScreen
    val expenses = viewModel.expenses.toList()

    // checking in which screen the user is at the moment
    when (currentScreen) {
        // displaying appropriate screen
        Screen.AddExpense -> {
            var newExpense by remember { mutableStateOf(Expense("", "")) }

            AddExpenseScreen(
                viewModel = viewModel,
                navigateToViewExpenses = {
                    // getting the route for each specific screen
                    viewModel.addExpense(newExpense)
                    viewModel.navigateTo(Screen.ViewExpenses)
                    navController.navigate(Screen.ViewExpenses.route)
                }
            )
        }
        // displaying appropriate screen
        Screen.ViewExpenses -> {
            ViewExpensesScreen(expenses = expenses,
                navigateToAddExpense = {
                    // getting the route for each specific screen
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