package com.blannonnetwork.loginwithfirebaseauth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun navScreen(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login"){
        composable("login"){
            LoginScreen(navController)
        }
        composable("signup"){
            SignUpScreen(navController)
        }
        composable("home"){
            HomeScreen(navController)
        }


    }

}