package com.example.yemekcalendar.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.yemekcalendar.authentication.presentation.components.CustomButton
import com.example.yemekcalendar.authentication.presentation.components.Description
import com.example.yemekcalendar.authentication.presentation.components.EmailTextField
import com.example.yemekcalendar.authentication.presentation.components.LanguageDropdownList
import com.example.yemekcalendar.authentication.presentation.components.Redirection
import com.example.yemekcalendar.authentication.presentation.viewmodel.ResetPasswordViewModel
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.Title

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    resetPasswordViewModel.initialize()

    val resetPasswordState by resetPasswordViewModel.resetPasswordState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (resetPasswordState.isSuccess) {
        AlertDialog(
            onDismissRequest = {
                resetPasswordViewModel.onResetPasswordDialogDismiss()
                navController.navigate(Screen.LoginScreen)
            },
            title = { Text(text = stringResource(id = R.string.reset_password_dialog_title)) },
            text = { Text(text = stringResource(id = R.string.reset_password_dialog_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        resetPasswordViewModel.onResetPasswordDialogDismiss()
                        navController.navigate(Screen.LoginScreen)
                    }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    if (resetPasswordState.isError) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, resetPasswordState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        LanguageDropdownList(
            selectedLanguage = resetPasswordState.selectedLanguage,
            onLanguageSelected = { resetPasswordViewModel.onLanguageSelected(it) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            //Login Title
            Title(
                modifier = Modifier.padding(bottom = 10.dp),
                text = stringResource(id = R.string.reset_password),
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            //Description
            Description(
                text = stringResource(id = R.string.reset_password_description)
            )

            //Loading indicator when resetting process is in progress
            if (resetPasswordState.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            //Email TextField
            EmailTextField(
                modifier = Modifier.fillMaxWidth(),
                email = resetPasswordState.email,
                emailErrorMessage = resetPasswordState.emailErrorMessage,
                onValueChange = { resetPasswordViewModel.onEmailValueChanged(it) },
                imeAction = ImeAction.Done
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            //Reset Button
            CustomButton(
                label = stringResource(id = R.string.reset),
                onClick = { resetPasswordViewModel.onResetButtonClicked() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 20.dp,
                        start = 30.dp,
                        end = 30.dp,
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Login redirection
            Redirection(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
                text = stringResource(id = R.string.already_have_account_message),
                onClick = { navController.navigate(Screen.LoginScreen) }
            )

            //Registration redirection
            Redirection(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
                text = stringResource(id = R.string.dont_have_account_message),
                onClick = { navController.navigate(Screen.RegisterationScreen) }
            )

        }
    }
}