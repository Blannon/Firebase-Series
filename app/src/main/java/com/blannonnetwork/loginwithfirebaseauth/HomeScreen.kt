package com.blannonnetwork.loginwithfirebaseauth

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun HomeScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition()
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    Box (
        modifier = Modifier.fillMaxSize()
            .background(
                color = Color(0xFFf5576c),
            ),
        contentAlignment = Alignment.Center
    ){
        repeat(3){
            Box(
                modifier = Modifier
                    .size(200.dp + it * 50.dp)
                    .offset(
                        x = (50 + it * 100).dp + floatingOffset.dp * (it + 1),
                        y = (100 + it * 150).dp + floatingOffset.dp * (2 - it)
                    )
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        RoundedCornerShape(50)
                    )
                    .blur(20.dp)
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                "Welcome to Firebase Android Series",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        FloatingActionButton(onClick ={
            Firebase.auth.signOut()
            navController.navigate("login"){
                popUpTo("login") { inclusive = true }
            }
        },
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(bottom = 35.dp, end = 1.dp)
                .size(100.dp),
            containerColor = Color.White,
            contentColor = Color.Black,
            shape = CircleShape
        ) {
            Text(
                text ="Logout",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )
        }
    }
}