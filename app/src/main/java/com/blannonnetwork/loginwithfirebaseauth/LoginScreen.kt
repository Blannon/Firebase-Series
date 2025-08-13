package com.blannonnetwork.loginwithfirebaseauth

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import com.dotlottie.dlplayer.Mode
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource

@Composable
fun LoginScreen(navController: NavHostController){

    var email by remember{mutableStateOf("")}
    var password by remember{mutableStateOf("")}
    var forgotPassword by remember{mutableStateOf(false)}
    var passwordVisible by remember{mutableStateOf(false)}
    var isLoading by remember{mutableStateOf(false)}
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

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

    val buttonScale by animateFloatAsState(
        targetValue = if (isLoading) 0.9f else 1f,
        animationSpec  = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(
                color = Color(0xFFf5576c),
            )
            .padding(16.dp),
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
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Url("https://lottie.host/9086da18-c205-44ba-b5bc-38627c24ef97/RW1pcQ0hl3.lottie"),
                autoplay = true,
                loop = true,
                speed = 1f,
                useFrameInterpolation = true,
                playMode = Mode.FORWARD,
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(30.dp)
            ){

            }
            Text(
                "Welcome To Firebase Android Series",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Login to continue",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Email",
                        color = Color.White.copy(alpha = 0.8f)
                    ) },
                modifier = Modifier.padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
                textStyle = TextStyle(
                    Color.White,
                    fontSize = 20.sp
                ),

                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    if (email.isNotEmpty()) {
                        IconButton(onClick = { email = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear email",
                                tint = Color.White.copy(alpha = 0.8f),
                            )
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        tint = Color.White.copy(alpha = 0.8f),
                        contentDescription = "Email"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White.copy(alpha = 0.8f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(
                    "Password",
                    color = Color.White.copy(alpha = 0.8f)
                ) },
                modifier = Modifier.padding(bottom = 8.dp)
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester),
                textStyle = TextStyle(
                    Color.White,
                    fontSize = 20.sp
                ),

                singleLine = true,
                visualTransformation =
                    if (passwordVisible)VisualTransformation.None
                        else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = {
                    Row {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle password visibility",
                                tint = Color.White,
                            )
                        }
                    }
                    if (password.isNotEmpty()) {
                        IconButton(onClick = { password = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear password",
                                tint = Color.White,
                            )
                        }
                    }
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Password",
                        tint = Color.White,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White.copy(alpha = 0.8f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                    cursorColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val context = LocalContext.current

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    isLoading = true
                    Firebase.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Successfully Logged In",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Login failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .scale(buttonScale),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Login",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.Black
                )

            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                forgotPassword = true
            }) {
                Text(
                    "forgot password",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (forgotPassword) {
                var resetEmail by remember { mutableStateOf("") }
                val context = LocalContext.current
                AlertDialog(
                    onDismissRequest = {
                        forgotPassword = false
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(10.dp),
                    title = { Text(
                        "Reset Password",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    ) },
                    text = {
                        Column {
                            Text(
                                "Enter your email to reset your password.",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge
                                )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = resetEmail,
                                onValueChange = { resetEmail = it },
                                label = { Text("Email") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (resetEmail.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter your email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@TextButton
                                }
                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(resetEmail).matches()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter a valid email address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@TextButton
                                }
                                Firebase.auth.sendPasswordResetEmail(resetEmail)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Password reset email sent",
                                                Toast.LENGTH_SHORT) .show()
                                            forgotPassword = false
                                            } else {
                                            Toast.makeText(
                                                context,
                                                "Failed to send password reset email: ${task.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ){
                            Text("Submit")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                forgotPassword = false
                            }
                        ) {
                            Text(
                                "Cancel",
                                color = Color.Black,
                                )
                        }
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Don't have an account? ",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                TextButton(
                    onClick = {
                        navController.navigate("signup") {
                            popUpTo("signup") { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        "Sign Up",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

        }

    }
}

