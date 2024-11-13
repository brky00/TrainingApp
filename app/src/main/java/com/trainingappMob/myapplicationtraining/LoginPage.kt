// LoginPage
package com.trainingappMob.myapplicationtraining

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

@Composable
fun LoginPage(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EA),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        TextField(value = email,
            onValueChange = { email = it },
            label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(navController.context, "Login successful", Toast.LENGTH_LONG).show()
                    navController.navigate("home_page")
                } else {
                    Toast.makeText(navController.context, "${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }) {
            Text("Login")
        }
        Button(onClick = { navController.navigate("welcome_page") {
            popUpTo("login_page") { inclusive = true }
        } }) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Go back"
            )
            Text("Go back to welcome page")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    MyApplicationTrainingTheme {
        val navController = rememberNavController()
        LoginPage(navController = navController)
    }
}


