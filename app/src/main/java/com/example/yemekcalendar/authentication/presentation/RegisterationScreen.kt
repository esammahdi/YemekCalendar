package com.example.yemekcalendar.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import com.example.yemekcalendar.authentication.presentation.viewmodel.RegistrationViewModel
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.Title

@Composable
fun RegisterationScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    registrationViewModel.initialize()

    val registrationState by registrationViewModel.registrationState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (registrationState.isSuccess) {
        AlertDialog(
            onDismissRequest = {
                registrationViewModel.onRegistrationSuccessDialogDismiss()
                navController.navigate(Screen.LoginScreen)
            },
            title = { Text(text = stringResource(id = R.string.registration_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.registration_dialog_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        registrationViewModel.onRegistrationSuccessDialogDismiss()
                        navController.navigate(Screen.LoginScreen)
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    if (registrationState.isError) {
        LaunchedEffect(key1 = registrationState.isError) {
            Toast.makeText(context, registrationState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    BaseAuthenticationLayout(
        selectedLanguage = registrationState.selectedLanguage,
        onLanguageSelected = { registrationViewModel.onLanguageSelected(it) }
    ) {
        // Registration Title
        Title(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(id = R.string.create_account),
        )

        // Registration Description
        Description(text = stringResource(id = R.string.create_account_description))

        Spacer(modifier = Modifier.height(16.dp))

        // Loading Indicator while processing registration request
        if (registrationState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        //Email TextField
        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            email = registrationState.email,
            emailErrorMessage = registrationState.emailErrorMessage,
            onValueChange = { registrationViewModel.onEmailValueChanged(it) },
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Confirmation Text Field
        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.repeat_email),
            email = registrationState.repeatEmail,
            emailErrorMessage = registrationState.repeatEmailErrorMessage,
            onValueChange = { registrationViewModel.onRepeatEmailValueChanged(it) },
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Text Field
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            password = registrationState.password,
            passwordErrorMessage = registrationState.passwordErrorMessage,
            onValueChange = { registrationViewModel.onPasswordValueChanged(it) },
            imeAction = ImeAction.Next,
            showPassword = registrationState.showPassword,
            onShowPasswordClicked = { registrationViewModel.onShowPasswordClicked() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Repeat Password Text Field
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.repeat_password),
            password = registrationState.repeatPassword,
            passwordErrorMessage = registrationState.repeatPasswordErrorMessage,
            onValueChange = { registrationViewModel.onRepeatPasswordValueChanged(it) },
            imeAction = ImeAction.Done,
            showPassword = registrationState.showPassword,
            onShowPasswordClicked = { registrationViewModel.onShowPasswordClicked() }
        )


        // Register Button
        CustomButton(
            label = stringResource(id = R.string.register),
            onClick = { registrationViewModel.onRegisterButtonClicked() },
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
            text = stringResource(id = R.string.already_have_account_message),
            onClick = { navController.navigate(Screen.LoginScreen) }
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

