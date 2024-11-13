package com.trainingappMob.myapplicationtraining

import BottomNavBar
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

@Composable
fun ProfilPage(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("Loading...") }
    var email by remember { mutableStateOf("Loading...") }
    var username by remember { mutableStateOf("Loading...") }
    var birthdate by remember { mutableStateOf("Loading...") }
    var totalScore by remember { mutableStateOf("0") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val currentUser = auth.currentUser

    // Fetch user info from Firestore
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        name = document.getString("name") ?: "N/A"
                        email = document.getString("email") ?: "N/A"
                        username = document.getString("username") ?: "N/A"
                        birthdate = document.getString("birthdate") ?: "N/A"
                        totalScore = (document.getLong("totalScore")?.toString()) ?: "0"
                    }
                }
                .addOnFailureListener { exception ->
                    errorMessage = "Failed to load profile data: ${exception.message}"
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profil image and name
            Card(
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .size(120.dp)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = name,
                style = TextStyle(
                    color = Color(0xFF6200EA),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Edit Profile button
            IconButton(
                onClick = {
                    navController.navigate("edit_profile_page")
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit Profile",
                    tint = Color(0xFF6200EA)
                )
            }

            // Total Score Card
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EA)),
                modifier = Modifier
                    .size(100.dp)
                    .padding(vertical = 16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = totalScore,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Text(
                text = "Total Score",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF6200EA)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // User account info and rows
            ProfileInfoRow(
                icon = Icons.Filled.Email,
                label = "Email",
                value = email
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            ProfileInfoRow(
                icon = Icons.Filled.AccountCircle,
                label = "Username",
                value = username
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            ProfileInfoRow(
                icon = Icons.Filled.CalendarToday,
                label = "D.O.B",
                value = birthdate
            )
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)

            // Logout Button
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    auth.signOut()
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("login_page") {
                        popUpTo("profil_page") { inclusive = true } // remove profil page from stack
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Text(text = "Logout", color = Color.White)
            }
        }
    }
}

@Composable
fun ProfileInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label icon",
            tint = Color(0xFF6200EA),
            modifier = Modifier
                .size(32.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilPagePreview() {
    MyApplicationTrainingTheme {
        val navController = rememberNavController()
        ProfilPage(navController = navController)
    }
}
