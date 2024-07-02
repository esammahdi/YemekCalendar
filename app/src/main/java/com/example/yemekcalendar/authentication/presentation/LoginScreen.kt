package com.example.yemekcalendar.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.yemekcalendar.R
import com.example.yemekcalendar.authentication.presentation.components.BaseAuthenticationLayout
import com.example.yemekcalendar.authentication.presentation.components.CustomButton
import com.example.yemekcalendar.authentication.presentation.components.Description
import com.example.yemekcalendar.authentication.presentation.components.EmailTextField
import com.example.yemekcalendar.authentication.presentation.components.PasswordTextField
import com.example.yemekcalendar.authentication.presentation.components.Redirection
import com.example.yemekcalendar.authentication.presentation.viewmodel.LoginViewModel
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.Title

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    loginViewModel.initialize()
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    if (loginState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.HomeScreen) {
                popUpTo(Screen.LoginScreen) { inclusive = true }
            }
        }
    }

    BaseAuthenticationLayout(
        selectedLanguage = loginState.selectedLanguage,
        onLanguageSelected = { loginViewModel.onLanguageSelected(it) }
    )
    {
            if (loginState.isError) {
                LaunchedEffect(Unit) {
                    Toast.makeText(context, loginState.errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            //Login Title
            Title(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(id = R.string.login),
            )

            //Login Description
            Description(text = stringResource(id = R.string.login_description))

            Spacer(modifier = Modifier.height(16.dp))

            //Loading indicator when login is in progress
            if (loginState.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                }
            }

            //Email TextField
            EmailTextField(
                modifier = Modifier.fillMaxWidth(),
                email = loginState.email,
                emailErrorMessage = loginState.emailErrorMessage,
                onValueChange = { loginViewModel.onEmailValueChanged(it) },
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Password TextField
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                password = loginState.password,
                passwordErrorMessage = loginState.passwordErrorMessage,
                onValueChange = { loginViewModel.onPasswordValueChanged(it) },
                imeAction = ImeAction.Done,
                showPassword = loginState.showPassword,
                onShowPasswordClicked = { loginViewModel.onShowPasswordClicked() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        loginViewModel.onRememberUserChanged(!loginState.rememberUser)
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = loginState.rememberUser,
                    onCheckedChange = {
                        loginViewModel.onRememberUserChanged(it)
                    }
                )
                Text(text = stringResource(id = R.string.remember_me))
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Login Button
            CustomButton(
                label = stringResource(id = R.string.login),
                onClick = { loginViewModel.onLoginButtonClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp,
                        start = 30.dp,
                        end = 30.dp,
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Sign Up redirection
            Redirection(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Start),
                text = stringResource(id = R.string.dont_have_account_message),
                onClick = { navController.navigate(Screen.RegisterationScreen) }
            )

            //Reset Password redirection
            Redirection(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
                text = stringResource(id = R.string.forgot_password_message),
                onClick = { navController.navigate(Screen.ResetPasswordScreen) }
            )
    }

}

