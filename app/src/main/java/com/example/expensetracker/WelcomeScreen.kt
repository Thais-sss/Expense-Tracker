// package declaration
package com.example.expensetracker

// importing important libraries, Compose
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// defining a Compose UI component
@Composable
// welcome screen function that takes two lambdas parameters
// they will handle the button click event
fun WelcomeScreen(onAddExpensesClick: () -> Unit, onViewExpensesClick: () -> Unit) {
    // applying shadow effect
    val shadowOffset = Offset(x= 8f, y=11f)
    val shadowColor = Color.White
    // using the default Material Theme to this block
    MaterialTheme{
        // creating a box
        Box(
            modifier = Modifier
                // taking up maximum size
                .fillMaxSize()
                // i went for a dark mode kind of style
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // adding the welcome text to welcome screen
            Text(
                // applying style to welcome text
                text = "Welcome!",
                // setting its color to be teal
                color = Color(0xFF03DAC6),
                style = TextStyle(
                    // setting the size of the font
                    fontSize = 70.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center, // Center the text
                    // applying the shadow effect
                    shadow = Shadow(shadowColor, shadowOffset, 2f)// Use h3 for a larger font
                )
            )
            // creating a vertical central column
            Column(
                modifier = Modifier.fillMaxSize(),
                // specifying position
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    // display an image from drawable resource
                    painter = painterResource(id = R.drawable.cover),
                    // giving a description for the image
                    contentDescription = "Digital Wallet",
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // ensure the image takes the full width
                        .height(300.dp) // adjusting the height
                        .clip(RoundedCornerShape(16.dp)) // applying rounded corners
                )
                Spacer(modifier = Modifier.height(45.dp)) // adding a vertical space between the image and the button
                ButtonWithIcon(
                    // calling the addExpensesClick
                    onClick = { onAddExpensesClick() },
                    icon = "add",
                    // display text on button
                    buttonText = "Add Expenses"
                )
                // adding space between the buttons
                Spacer(modifier = Modifier.height(30.dp)) // Add a vertical space between the buttons
                ButtonWithIcon(
                    // calling onViewExpensesClick
                    onClick = { onViewExpensesClick() },
                    icon = "eye", // Replace with the actual eye icon from resource
                    // adding text to the button
                    buttonText = "View Expenses"
                )
            }
        }
    }
}

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF03DAC6),
    icon: Any? = null,
    buttonText: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                if (it is Int) {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 12.dp)
                    )
                    //pacer(modifier = Modifier.width(8.dp))

                } else if (it is String && it == "add") {
                    // Replace "add" with the actual name of your image in the drawable folder
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 12.dp)
                    )
                    //Spacer(modifier = Modifier.height(35.dp)) // Adjust the spacing here
                } else if (it is String && it == "eye") {
                    // Replace "eye" with the actual name of your image in the drawable folder
                    Image(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 12.dp)
                    )
                }
            }
            Text(buttonText, color = Color.Black, fontSize = 22.sp)
        }
    }
}

// adding preview compose that we learn in class
// in order to have a live display of the current screen
// while updating it
@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen({}, {})
}
