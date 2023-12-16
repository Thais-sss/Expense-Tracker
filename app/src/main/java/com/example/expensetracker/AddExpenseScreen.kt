package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseScreen(viewModel: YourViewModel, navController: NavController, navigateToViewExpenses: () -> Unit) {
    // this composable function will render the UI for the AddExpenseScreen
    // it takes YourViewModel and navigateToViewExpenses as parameters
    MaterialTheme {
        // setting up a vertical column
        // added some design here
        // such as background as black
        // allows it to take up all available space
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center // Center the children vertically
        ) {
            BoxWithConstraints(
                // used BoxWithConstraints in order to avail of the Transparent feature
                // to create that transition in my picture
                modifier = Modifier
                    .height(350.dp)
                //.weight(1f) // Take up the remaining available space
            ) {
                val gradientHeight = maxHeight

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.o3),
                        contentDescription = "Bottom Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Spacer(
                        // block of code used to style image
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gradientHeight)
                            .background(
                                Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        // give the effect of transparency to the image
                                        Pair(0.4f, Transparent),
                                        Pair(1f, White)
                                    )
                                )
                            )
                    )
                }
            }
            // creating variable to store if the fields are filled or empty
            var showError by remember { mutableStateOf(false) }

            // declaring some variables
            val expenseNameState = remember { mutableStateOf("") }
            // specifying that their value will change or at least can change
            val amountState = remember { mutableStateOf("") }

            val myColor = Color(0xFF03DAC6)
            // text fields for expense details
            Text(
                text = "Expense Name", // Your label text
                color = Color.White, // Adjust label text color
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                // saving the value
                value = expenseNameState.value, // Use state to manage input
                onValueChange = { expenseNameState.value = it },
                textStyle = TextStyle(
                    color = Color.White, // Set text color to white
                    fontSize = 16.sp // Adjust font size as needed
                ),
                modifier = Modifier
                    .border(
                        // setting design for fields
                        // gradient for the border of the fields
                        border = BorderStroke(
                            brush =
                            Brush.linearGradient(listOf(Color.Red, Color(0xFF03DAC6))),
                            width = 2.dp,
                        ),
                        shape = CutCornerShape(12.dp)
                    )
            )

            Text(
                text = "Amount", // my label text
                color = Color.White, // adjust label text color
                modifier = Modifier.padding(8.dp)
            )
            TextField(
                // saving the value
                value = amountState.value, // Use state to manage input
                onValueChange = { amountState.value = it },
                textStyle = TextStyle(
                    color = Color.White, // Set text color to white
                    fontSize = 16.sp // Adjust font size as needed
                ),
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            brush =
                            Brush.linearGradient(listOf(Color.Red, Color(0xFF03DAC6))),
                            width = 2.dp,
                        ),
                        shape = CutCornerShape(12.dp)
                    )
                    .padding(4.dp)
            )
            // checking the variable error to see if it is true or not
            // if it true, to display an error message asking user to input
            // expense name and expense amount
            if (showError) {
                Text("Please enter both name and amount", color = Color.Red)
            }
            // Submit Button
            GradientButton( // design used to have colors in a gradient
                // around the button
                text = "Submit",
                onClick = {
                    val name = expenseNameState.value
                    val amount = amountState.value

                    if (name.isNotBlank() && amount.isNotBlank()) {
                        val newExpense = Expense(name, amount)
                        viewModel.addExpense(newExpense)
                        navigateToViewExpenses()

                        expenseNameState.value = ""
                        amountState.value = ""
                    } else {
                        showError = true
                        // Show error or handle as needed
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(8.0f)
                    .padding(8.dp)
            )
        }
    }
}


@Composable
fun ViewExpenses(expenses: List<Expense>) {
    // Display the list of expenses
    LazyColumn {
        // rendering items of the list
        items(expenses) { expense ->
            // printing them
            Text("Name: ${expense.name}, Amount: ${expense.amount}")
        }
    }
}
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CutCornerShape(12.dp),
    backgroundColor: Color = Color.Transparent,
    textColor: Color = Color.Black,
) {
    val gradient = Brush.horizontalGradient(listOf(Color.Red, Color(0xFF03DAC6)))

    // button with the onClick feature
    Button(
        onClick = onClick,
        modifier = modifier
            // styling the button
            .background(gradient)
            .border(
                border = BorderStroke(width = 8.dp, brush = Brush.linearGradient(listOf(Color.Red, Color(0xFF03DAC6)))),
                shape = shape
            )
            .padding(4.dp),
    ) {
        // passing the text and its colour
        // i wnet for white in most cases as i am using a dark background
        Text(text = text, color = Color.White)
    }
}

// preview was a function showed to us in class
// it really helped to check the ui and some changes live
// instead of running the application every time
// a very handy function used a lot while trying to implement some ui
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AddExpenseScreenPreview() {
    val viewModel = YourViewModel()
    val navController = rememberNavController()
    AddExpenseScreen(viewModel = viewModel, navController = navController) {}
}
