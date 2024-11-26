package com.trainingappMob.myapplicationtraining

import BottomNavBar
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trainingappMob.myapplicationtraining.components.MealCard
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle


// version new version new last
//last
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavHostController, viewModel: MealViewModel) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        // getting data from viewmodel
        val meals = viewModel.meals.collectAsState().value
        val loading by viewModel.loading.collectAsState()
        var selectedGoal by remember { mutableStateOf<String?>("Please choose a goal") }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title: Fitmeals
            item {
                Text(
                    text = "Fitmeals",
                    color = Color(0xFF6200EA),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp) // Avstand til toppen og mellomrom til Score
                )
            }


            // Score Component (Start-aligned)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp), // Justering for å plassere til venstre
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp)
                            .border(2.dp, Color.White, shape = CircleShape),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EA))
                    ) {
                        Box( contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                text = "50",
                                color = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }


            // Choose Your Goal Text
            item {
                Text(
                    text = "Choose your goal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            // Goal Selection Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Build Muscle Card
                    Card(
                        modifier = Modifier
                            .size(95.dp)
                            .clickable {
                                selectedGoal = "Build muscle"
                                viewModel.loadMeals("build muscle")
                            },
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, Color(0xFF800080))
                    ) {
                        Box( modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.buildmuscles),
                                contentDescription = "Card Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.8f)
                            )
                            Text(
                                text = "Build muscle",
                                modifier = Modifier.align(Alignment.Center).padding(8.dp),
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF6200EA),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Lose Weight Card
                    Card(
                        modifier = Modifier
                            .size(95.dp)
                            .clickable {
                                selectedGoal = "Lose weight"
                                viewModel.loadMeals("lose weight")
                            },
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, Color(0xFF800080))
                    ) {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.loseweight),
                                contentDescription = "Lose weight",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "Lose weight",
                                modifier = Modifier.align(Alignment.Center).padding(8.dp),
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF6200EA),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Selected Goal or Placeholder
            item {
                Text(
                    text = selectedGoal ?: "Please choose a goal",
                    fontSize = if (selectedGoal == "Please choose a goal") 23.sp else 24.sp,
                    fontWeight = if (selectedGoal == "Please choose a goal") FontWeight.SemiBold else FontWeight.Bold,
                    color = if (selectedGoal == "Please choose a goal") Color.Black else Color(0xFF6200EA),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Meals Section
            if (loading) {
                item {
                    Text(
                        text = "Loading...",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else if (selectedGoal != "Please choose a goal") {
                items(meals.chunked(2)) { rowMeals ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowMeals.forEach { meal ->
                            MealCard(meal = meal)
                        }
                    }
                }
            }
        }
    }
}








