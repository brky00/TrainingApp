package com.trainingappMob.myapplicationtraining

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditProfilePage(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }

    val currentUser = auth.currentUser


    // Fetching current user from the firestore
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        name = document.getString("name") ?: ""
                        email = document.getString("email") ?: ""
                        username = document.getString("username") ?: ""
                        birthdate = document.getString("birthdate") ?: ""
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6200EA))

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text("Birthdate") })
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                currentUser?.let { user ->
                    val updatedData = mapOf(
                        "name" to name,
                        "email" to email,
                        "username" to username,
                        "birthdate" to birthdate
                    )

                    // Authentication email update with verification at email adress
                    user.verifyBeforeUpdateEmail(email).addOnSuccessListener {
                        Toast.makeText(context, "Verification email sent. Please check your inbox.", Toast.LENGTH_SHORT).show()

                        // Firestore will be updated after user accepted it at the email
                        firestore.collection("users").document(user.uid).update(updatedData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Verify your email address to update it completely. Everything else is Updated", Toast.LENGTH_SHORT).show()
                                navController.popBackStack() // back to ProfilPage
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to update profile in Firestore", Toast.LENGTH_SHORT).show()
                            }
                    }.addOnFailureListener {
                        Toast.makeText(context, "Failed to send verification email: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))) {
                Text(text = "Save", color = Color.White)
            }
            Button(
                onClick = {
                    navController.navigate("profil_page") {
                        popUpTo("edit_profile_page") { inclusive = true } // EditProfilePage removed from stack
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Back to Profile", color = Color.White)
            }



        }
    }
}


