package com.trainingappMob.myapplicationtraining

import BottomNavBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.trainingappMob.myapplicationtraining.R

@Composable
fun ProfilPage(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("Loading...") }
    var email by remember { mutableStateOf("Loading...") }
    var username by remember { mutableStateOf("Loading...") }
    var birthdate by remember { mutableStateOf("Loading...") }

    val currentUser = auth.currentUser

    // Firestore'dan kullanıcı verilerini çekme
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        name = document.getString("name") ?: "N/A"
                        email = document.getString("email") ?: "N/A"
                        username = document.getString("username") ?: "N/A"
                        birthdate = document.getString("birthdate") ?: "N/A"
                    }
                }
                .addOnFailureListener {
                    // Hata durumunda işlemler
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
            // Profil Resmi ve İsim
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

            // Kullanıcı Bilgileri Satırları
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
