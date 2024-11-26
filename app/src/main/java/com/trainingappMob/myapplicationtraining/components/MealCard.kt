package com.trainingappMob.myapplicationtraining.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.trainingappMob.myapplicationtraining.R
import com.trainingappMob.myapplicationtraining.model.Meal

@Composable
fun MealCard(meal: Meal) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(150.dp)
            .padding(8.dp),
    ) {
        Column {
            val imageResId = getImageResourceId(meal.imageName)

            // Bruk Coil med minneoptimalisering (skaler bildet til ønsket størrelse)
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageResId)
                    .apply {
                        // Optimalisering: spesifiser maksimal størrelse for bildet
                        size(120, 120) // Tilpass størrelsen til visningen
                        scale(Scale.FIT) // Behold bildets proporsjoner
                    }
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            )
            Text(
                text = meal.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Composable
fun getImageResourceId(imageName: String): Int {
    return when (imageName) {
        "salad" -> R.drawable.salad
        "greekpastasalad" -> R.drawable.greekpastasalad
        "cauliflowerwhitesauce" -> R.drawable.cauliflowerwhitesauce
        "salmon" -> R.drawable.salmon
        "beansvegetables" -> R.drawable.beansvegetables
        "yogurtberries" -> R.drawable.yogurtberries
        "chicken" -> R.drawable.chicken
        "biff" -> R.drawable.biff
        "prawnsnoodles" -> R.drawable.prawnsnoodles
        "eggsalad" -> R.drawable.eggsalad

        else -> R.drawable.nofoodimage // default image
    }
}

@Preview(showBackground = true)
@Composable
fun MealCardPreview() {
    val sampleMeal = Meal(
        name = "cauliflower with white sauce",
        calories = 120,
        protein = 14,
        imageName = "cauliflowerwhitesauce"
    )
    MealCard(meal = sampleMeal)
}