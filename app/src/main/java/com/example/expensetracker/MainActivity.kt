// stating package name
package com.example.expensetracker

// importing libraries, components
// firebase app from google
import android.os.Build
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

// declaring main activity
// which extends component activity
class MainActivity : ComponentActivity() {
    // setting viewModel
    private val viewModel: YourViewModel by viewModels()

    // create variable to store instance of firebase firestore
    private lateinit var firestore: FirebaseFirestore

    // the main activity is where everything starts
    // so this will call the prompt to ask for permission
    // to track user location and it will also initialize firebase
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize firebase
        FirebaseApp.initializeApp(this)

        // request user permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                // access location
                // this was also added in the manifest file
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        // Start the LocationService
        Intent(applicationContext, LocationService::class.java).apply {
            // call the start action
            action = LocationService.ACTION_START
            // start the service
            startService(this)
        }
        viewModel.startLocationService(applicationContext)
        // i added some logs to ensure my location tracking
        // was working properly
                        Log.d("THAIS KEY: Started","here")

        setContent { // setting content
            // creating instance of navController
            val navController = rememberNavController()

            // setting the landing screen to be the welcome screen
            NavHost(navController = navController, startDestination = "welcome_route") {
                // composing welcome screen
                // starting the welcome route
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
                            navController.navigate(Screen.AddExpense.route)
                        },
                        onDeleteClick = { expense ->
                            // Handle the delete action here
                            viewModel.deleteExpense(expense)
                        }
                    )
                }
            }
        }
    }
}

// defining a function to apply a theme
@Composable
fun BackgroundLocationTrackingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = typography,
        content = content
    )
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
        // if it is addexpense, then render add expense screen
        Screen.AddExpense -> {
            var newExpense by remember { mutableStateOf(Expense("", "")) }

            // render add expense screen
            AddExpenseScreen(
                viewModel = viewModel,
                navController = navController,
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
            ViewExpensesScreen(
                expenses = expenses,
                navigateToAddExpense = {
                    // getting the route for each specific screen
                    viewModel.navigateTo(Screen.AddExpense)
                    navController.navigate(Screen.AddExpense.route)
                },
                onDeleteClick = { expense ->
                    // Handle the delete action here
                    viewModel.deleteExpense(expense)
                }
            )
        }
    }
}


// preview composable to display a live
// preview of what this screen would like
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ExpenseTrackerAppPreview() {
    ExpenseTrackerApp(viewModel = YourViewModel(), navController = rememberNavController())
}