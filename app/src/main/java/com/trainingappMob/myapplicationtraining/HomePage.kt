package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trainingappMob.myapplicationtraining.components.MealCard
import com.trainingappMob.myapplicationtraining.viewService.MealViewModel

//version 1 11 11 111
@Composable
fun HomePage(navController: NavHostController,
             viewModel: MealViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
//states
        val meals = viewModel.meals.collectAsState().value
        val loading by viewModel.loading.collectAsState()
        var selectedGoal by remember { mutableStateOf<String?>("Please choose a goal") }


        // backgorund image
        Image(
            painter = painterResource(id = R.drawable.fitnessimage),
            contentDescription = "Fitness Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .graphicsLayer(alpha = 0.5f) // Opacity 0.5
        )

        // contents
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween, // for diffrence between top and bottom content
            horizontalAlignment = Alignment.CenterHorizontally // horizontal
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Fitmeals",
                    color = Color(0xFF6200EA), // purple color
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold, // bold font
                    modifier = Modifier
                        .padding(top = 20.dp) // top padding
                )
                Row(modifier = Modifier.fillMaxWidth().padding(top = 50.dp)) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .size(40.dp) // Circle card size
                            .border(2.dp, Color.White, shape = CircleShape), // outline white
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EA))


                    ) {
                        Box(
                            contentAlignment = Alignment.Center, // This center the contens inside the box.
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                text = "50",
                                color = Color.White, // White text Color
                                modifier = Modifier.padding(8.dp) // padding for the content
                            )
                        }
                    }

                }

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Choose your goal",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                )
                //  Row Top (for cards)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Card(
                        modifier = Modifier
                            .size(95.dp, 95.dp) //Card sizes
                            .clickable {
                                selectedGoal = "build muscle"
                                viewModel.loadMeals("build muscle") // loading goals meal
                            },
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, Color(0xFF800080))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.buildmuscles),
                                contentDescription = "Card Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                                    .graphicsLayer(alpha = 0.8f)

                            )
                            Text(
                                text = "Build muscle",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp),
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF6200EA),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .size(95.dp, 95.dp)
                            .clickable {
                                selectedGoal = "lose weight"
                                viewModel.loadMeals("lose weight") // Hedefe göre veri yükle
                            },
                        shape = RoundedCornerShape(100.dp),
                        border = BorderStroke(1.dp, Color(0xFF800080))
                    ) {
                        Box {
                            Image(
                                painter = painterResource(id = R.drawable.loseweight),
                                contentDescription = "Card Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                                    .graphicsLayer(alpha = 0.8f)
                            )
                            Text(
                                text = "Lose weight",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp),
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF6200EA),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 40.dp)
                ) {

                    Text(
                        text = selectedGoal ?: "Please choose a goal",
                        fontSize = if (selectedGoal == "Please choose a goal") 23.sp else 24.sp,
                        fontWeight = if (selectedGoal == "Please choose a goal") FontWeight.SemiBold else FontWeight.Bold,
                        color = if (selectedGoal == "Please choose a goal") Color.Black else Color(
                            0xFF6200EA
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )


                    //  2rows 1 for recommended meals(2 meal images in this row)
                    if (loading) {
                        Text(
                            text = "Loading...",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else if (selectedGoal != "Please choose a goal") {
                        // Conditional render
                        Text(
                            text = "Recommended meals for your goal:",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        meals.chunked(2).forEach { rowMeals ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowMeals.forEach { meal ->
                                    MealCard(meal)
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}




