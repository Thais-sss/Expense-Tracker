// name of the package
package com.example.expensetracker

// necessary imports like composable
// lazy column, material theme, button, text
// mutable state of and so on
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource

// declaring ViewExpensesScreen composable function
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewExpensesScreen(
    // passing expenses, navigate to add expense and on delete click
    expenses: List<Expense>,
    navigateToAddExpense: () -> Unit,
    onDeleteClick: (Expense) -> Unit
) {
    // declaring variables for totalexpense when they are added together
    var totalExpense by remember { mutableDoubleStateOf(0.0) }
    // declaring variable for city name
    var cityName by remember { mutableStateOf("Unknown City") }

    // first validating and checking that name and amount are both not blank
    totalExpense = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }
        // casting amount to double
        .sumByDouble { it.amount.toDouble() }

    // variable to validate expenses
    val validExpenses = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }

    // Get the city name from the location service
    LocationService.currentCityFlow.collectAsState().value?.let { cityNameValue ->
        // storing the value in city name
        cityName = cityNameValue
    }

    // using material theme to apply style
    MaterialTheme(
        // using a dark background
        colors = darkColors(
            // setting color of background text to be teal
            primary = Color(0xFF03DAC6),
            onPrimary = Color.Black
        )
    ) {
        // defining lazy column
        LazyColumn(
            modifier = Modifier
                // taking up max size
                .fillMaxSize()
                .background(Color.Black)
                // setting background to dark
                .padding(16.dp)
        ) {
            item {
                Button(
                    // adding a button that will navigate to the add expense screen
                    onClick = { navigateToAddExpense() },
                    modifier = Modifier
                        // customizing it
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFF03DAC6), shape = RoundedCornerShape(10.dp))
                ) {
                    // adding the text to the button
                    Text("Add Expense")
                }
                // adding a space
                Spacer(modifier = Modifier.height(8.dp))
                // printing the city name
                // to check that location is working
                Text("City: $cityName", color = Color.White)
            }

            // checking if expenses list is empty or not
            if (validExpenses.isEmpty()) {
                item {
                    Column(
                        // if valid expenses is empty, this column will be rendered
                        modifier = Modifier
                            // style
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // this message will be displayed
                        Text("No expenses added yet", modifier = Modifier.padding(16.dp), color = Color.White)
                    }
                }
            } else {
                // if expense list is not empty, then this column below will be rendered
                items(validExpenses) { expense ->
                    Column(
                        // adding style to it
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                // using teal color
                                Color(0xFF03DAC6),
                                // giving shape to it
                                shape = RoundedCornerShape(10.dp)
                            )
                            .animateContentSize()
                    ) {
                        Row(
                            // using modifier to add style
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                // displaying icon from drawable
                                painter = painterResource(id = R.drawable.cartt),
                                // giving a description to the icon
                                contentDescription = "Expense Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            // adding a space
                            Spacer(modifier = Modifier.width(8.dp))
                            // displaying expense name
                            Text("Name: ${expense.name}", modifier = Modifier.padding(8.dp))
                        }
                        // displaying expense amount
                        Text("Amount: ${expense.amount}", modifier = Modifier.padding(8.dp))
                        // Add delete icon with click listener
                        Image(
                            painter = painterResource(id = R.drawable.dele),
                            // giving a description to the icon
                            contentDescription = "Delete Expense",
                            // adding style
                            modifier = Modifier
                                .size(45.dp)
                                .padding(8.dp)
                                // calling the ondeleteclick and passing expense
                                .clickable { onDeleteClick(expense) }
                        )
                    }
                }

                item {
                    Box(
                        // box that will display the total expense
                        modifier = Modifier
                            // adding style to it
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                Color(0xFF03DAC6),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .animateContentSize()
                    ) {
                        // displaying text
                        Text("Total Expense: $totalExpense", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

