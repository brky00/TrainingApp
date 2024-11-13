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



            TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = birthdate, onValueChange = { birthdate = it }, label = { Text("Birthdate") })
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                currentUser?.let { user ->
                    val updatedData = mapOf(
                        "name" to name,
                        "username" to username,
                        "birthdate" to birthdate
                    )
                    firestore.collection("users").document(user.uid).update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // Back to ProfilPage
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        }
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))) {
                Text(text = "Save", color = Color.White)
            }

            // Back button to navigate to ProfilPage
            Button(
                onClick = {
                    navController.navigate("profil_page") {
                        popUpTo("edit_profile_page") { inclusive = true }
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


