package com.trainingappMob.myapplicationtraining

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.trainingappMob.myapplicationtraining.backend.googleSignIn.GoogleAuthClient


@Composable
fun GoogleSignInButton(
    navController: NavHostController,
    isSignIn: Boolean,
    googleAuthClient: GoogleAuthClient,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        if (isSignIn) {
            OutlinedButton(onClick = { onSignOutClick() }) {
                Text(
                    text = "Sign Out",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(
                        horizontal = 24.dp, vertical = 4.dp
                    )
                )
            }
        } else {


            OutlinedButton(onClick = { onSignInClick() }) {
                Text(
                    text = "Sign In With Google",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(
                        horizontal = 24.dp, vertical = 4.dp
                    )
                )
            }
        }
    }
}


