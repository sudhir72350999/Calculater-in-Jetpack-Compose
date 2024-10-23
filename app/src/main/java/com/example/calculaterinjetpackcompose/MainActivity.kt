package com.example.calculaterinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculaterinjetpackcompose.ui.theme.CalculaterInJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent {
//            CalculaterInJetpackComposeTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }


        setContent {

            CalculatorScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CalculaterInJetpackComposeTheme {
//        Greeting("Android")
//    }
//}


@Composable
fun CalculatorScreen() {
    var result by remember { mutableStateOf("0") }
    var currentInput by remember { mutableStateOf("") }

    val onNumberClick: (String) -> Unit = { number ->
        currentInput += number
        result = currentInput
    }

    val onOperatorClick: (String) -> Unit = { operator ->
        currentInput += " $operator "
        result = currentInput
    }

    val onClearClick: () -> Unit = {
        currentInput = ""
        result = "0"
    }

    val onEqualsClick: () -> Unit = {
        try {
            val expression = currentInput
            result = evaluateExpression(expression).toString()
            currentInput = result
        } catch (e: Exception) {
            result = "Error"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = result,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Row for number pad and operations
        CalculatorButtonGrid(
            onNumberClick = onNumberClick,
            onOperatorClick = onOperatorClick,
            onClearClick = onClearClick,
            onEqualsClick = onEqualsClick
        )
    }
}

@Composable
fun CalculatorButtonGrid(
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onClearClick: () -> Unit,
    onEqualsClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Row for numbers and operators
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton("7", onClick = { onNumberClick("7") })
            CalculatorButton("8", onClick = { onNumberClick("8") })
            CalculatorButton("9", onClick = { onNumberClick("9") })
            CalculatorButton("/", onClick = { onOperatorClick("/") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton("4", onClick = { onNumberClick("4") })
            CalculatorButton("5", onClick = { onNumberClick("5") })
            CalculatorButton("6", onClick = { onNumberClick("6") })
            CalculatorButton("*", onClick = { onOperatorClick("*") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton("1", onClick = { onNumberClick("1") })
            CalculatorButton("2", onClick = { onNumberClick("2") })
            CalculatorButton("3", onClick = { onNumberClick("3") })
            CalculatorButton("-", onClick = { onOperatorClick("-") })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton("0", onClick = { onNumberClick("0") })
            CalculatorButton("C", onClick = { onClearClick() })
            CalculatorButton("=", onClick = { onEqualsClick() })
            CalculatorButton("+", onClick = { onOperatorClick("+") })
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .background(Color.Gray, CircleShape)
    ) {
        Text(text = text, fontSize = 24.sp)
    }
}

// Function to evaluate basic arithmetic expression
fun evaluateExpression(expression: String): Double {
    val tokens = expression.split(" ")
    var result = tokens[0].toDouble()

    var i = 1
    while (i < tokens.size) {
        val operator = tokens[i]
        val nextValue = tokens[i + 1].toDouble()
        when (operator) {
            "+" -> result += nextValue
            "-" -> result -= nextValue
            "*" -> result *= nextValue
            "/" -> result /= nextValue
        }
        i += 2
    }

    return result
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    CalculatorScreen()
}