package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = {
            content()
        }
    )
}


@Composable
fun CustomIcon(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val tint = if (enabled) {
        LocalContentColor.current
    } else {
        LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
    }

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseScreen(viewModel: YourViewModel, navController: NavController, navigateToViewExpenses: () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                .padding(bottom = 80.dp),// setting size for the column,
               // verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally // aligning column
            ) {
                Text( // setting text to be displayed
                    text = "Add Expense",
                    modifier = Modifier.padding(32.dp)
                )

                // declaring some variables
                val expenseNameState = remember { mutableStateOf("") }
                // specifying that their value will change or at least can change
                val amountState = remember { mutableStateOf("") }

                val myColor = Color(0xFF03DAC6)
                // text fields for expense details
                TextField(
                    // saving the value
                    value = expenseNameState.value, // Use state to manage input
                    onValueChange = { expenseNameState.value = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription= "euro"
                        )
                    },
                    trailingIcon = {
                        Icon (
                            imageVector = Icons.Filled.Check,
                            contentDescription = "email icon"
                        )
                    },
                    // label for user
                    label = { Text("Expense Name") },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedLabelColor = myColor,
                        focusedLabelColor = myColor,
                        cursorColor = myColor,
                        textColor = myColor,
                        focusedIndicatorColor = myColor,
                        unfocusedIndicatorColor = myColor
                    ),
                    // jetpack tools to deal with input
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                TextField(
                    // same as above for amount now
                    value = amountState.value, // Use state to manage input
                    onValueChange = { amountState.value = it },
                    label = { Text("Amount") },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedLabelColor = myColor,
                        focusedLabelColor = myColor,
                        cursorColor = myColor,
                        textColor = myColor,
                        focusedIndicatorColor = myColor,
                        unfocusedIndicatorColor = myColor
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                // validate user input
                var showError by remember { mutableStateOf(false) }

                Spacer(modifier = Modifier.height(35.dp)) // Add space between Amount input and Submit button

                // button to add the expense
                // submit Button
                Button(
                    onClick = {
                        // Save the entered expense to a list, database, or ViewModel
                        val newExpense = Expense(expenseNameState.value, amountState.value)

                        // saving the values name and amount entered
                        val name = expenseNameState.value
                        val amount = amountState.value

                        // if statement to validate
                        if (name.isNotBlank() && amount.isNotBlank()) {
                            // checking if the fields are not blank
                            val newExpense = Expense(name, amount)
                            // if not blank, it will navigate user to the next screen
                            viewModel.addExpense(newExpense)
                            navigateToViewExpenses()

                            // Clear the text fields after submission
                            expenseNameState.value = ""
                            amountState.value = ""
                            // if the fields are blank, throw error
                        } else {
                            showError = true
                        }
                    },
                    // tools for design
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(70.dp),//like the other one
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC6))
                ) {
                    Text("Submit", color = Color.Black, fontSize = 24.sp)
                }
                if (showError) {
                    Text("Please enter both name and amount", color = Color.Red)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.o3),
                contentDescription = "Bottom Image",
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Ensure the Image takes the full width
                    .height(300.dp) // Adjust the height as needed
                    .clip(RoundedCornerShape(16.dp)) // Apply rounded corners
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Crop // Maintain aspect ratio
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF03DAC6), RoundedCornerShape(50))
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Arrow")
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        // Navigate to View Expenses when the button is clicked
                        navigateToViewExpenses()
                    },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color(0xFF03DAC6), RoundedCornerShape(10.dp))
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "View Expenses")
                }
            }
        }
        }
    }

@Composable
fun Spacer(modifier: Modifier) {

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
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun AddExpenseScreenPreview() {
    val viewModel = YourViewModel() // Replace with the actual initialization of your ViewModel
    val navController = rememberNavController()
    AddExpenseScreen(viewModel = viewModel, navController = navController) {}
}
