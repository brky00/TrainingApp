package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.trainingappMob.myapplicationtraining.ui.theme.MyApplicationTrainingTheme

@Composable
fun ProfilPage(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ProfilPage")
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
