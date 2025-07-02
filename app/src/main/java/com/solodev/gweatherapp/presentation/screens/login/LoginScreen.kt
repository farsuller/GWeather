package com.solodev.gweatherapp.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.solodev.gweatherapp.R
import com.solodev.gweatherapp.presentation.components.TextFieldOutline
import com.solodev.gweatherapp.ui.theme.ChromeYellow
import com.solodev.gweatherapp.util.isValidEmail

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(5.dp)
                    .size(140.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier.size(250.dp),
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo Image",
                    tint = ChromeYellow,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextFieldOutline(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isValidEmail(it) && it.isNotBlank()
                },
                label = "Email",
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextFieldOutline(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.isNotEmpty() && it.isNotBlank()
                },
                label = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.width(150.dp),
                onClick = navigateToHome,
                enabled = email.isNotBlank() && password.isNotBlank() && isEmailValid && isPasswordValid,
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(onClick = navigateToSignUp) {
                    Text(text = "Don't have an account? Sign-up here")
                }
            }
        }
    }
}
