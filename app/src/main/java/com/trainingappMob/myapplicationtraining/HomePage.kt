package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme



@Composable
fun HomePage(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {

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
    Row(modifier = Modifier.fillMaxWidth().padding(top=50.dp)) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(40.dp) // Circle card size
                .border(2.dp, Color.White, shape = CircleShape), // Beyaz kenarlık
            colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EA))


        ) {
            Box(
                contentAlignment = Alignment.Center, // Center the contens inside the box.
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "50",
                    color = Color.White, // White text Color
                    modifier = Modifier.padding(8.dp) // padding for the content
                )
            }
        }

    }

        Text(modifier=Modifier.padding(top=10.dp) ,text="Choose your goal")







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

                    ,
                shape = RoundedCornerShape(100.dp)
            , border = BorderStroke(1.dp, Color(0xFF800080))
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.buildmuscles),
                    contentDescription = "Card Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Build muscle",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                    color = Color.Black
                )
            }
        }

        Card(
            modifier = Modifier
                .size(95.dp, 95.dp),
            shape = RoundedCornerShape(100.dp),
            border = BorderStroke(1.dp, Color(0xFF800080))
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.loseweight),
                    contentDescription = "Card Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Lose weight",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                    color = Color.Black
                )
            }
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier= Modifier.padding(top=40.dp)){
        Text(text="Recomemmended meals for your goal")


        // row 1 for recommended meals(2 meal images in this row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), // paddin top
            horizontalArrangement = Arrangement.SpaceEvenly //  equal space between cards
        ) {
            Card(
                modifier = Modifier
                    .size(115.dp, 100.dp) // Card sizes
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.meal1),
                        contentDescription = "Card Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Card Title 1",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp),
                        color = Color.White
                    )
                }
            }

            Card(
                modifier = Modifier
                    .size(115.dp, 100.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.meal2),
                        contentDescription = "Card Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Card Title 2",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp),
                        color = Color.White
                    )
                }
            }
        }
        // row 2 for recommended meals(2 meal images in this row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp), // padding top
            horizontalArrangement = Arrangement.SpaceEvenly // space between cards down
        ) {
            Card(
                modifier = Modifier
                    .size(115.dp, 100.dp)  // card sizes
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.meal3),
                        contentDescription = "Card Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Card Title 1",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp),
                        color = Color.White
                    )
                }
            }

            Card(
                modifier = Modifier
                    .size(115.dp, 100.dp)
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.meal4),
                        contentDescription = "Card Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Card Title 2",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(8.dp),
                        color = Color.White
                    )
                }
            }
        }

    }
}


        }
    }
}
@Preview(showBackground = true)
@Composable
fun TrainingPreviewHome() {
    MyApplicationTrainingTheme {


        NavigationBetweenPages(
            navController = TODO()
        )



    }
}
