package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = stringResource(id = R.string.password),
    password: String,
    passwordErrorMessage: String,
    onValueChange: (String) -> Unit,
    showPassword: Boolean = false,
    onShowPasswordClicked: () -> Unit,
    imeAction: ImeAction = ImeAction.Done
) {
    //Password TextField
    TextField(
        label = { Text(label) },
        value = password,
        onValueChange = { onValueChange(it) },
        isError = passwordErrorMessage.isNotEmpty(),
        supportingText = {
            if (passwordErrorMessage.isNotEmpty()) {
                Text(
                    text = passwordErrorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier,
//        colors = TextFieldDefaults.colors().copy(
//            cursorColor = Color.Black,
//            disabledLabelColor = lightBlue,
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//        ),
        leadingIcon = {
            // Display the user's profile picture as the leading icon
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Password Lock Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = onShowPasswordClicked) {
                Icon(
                    painter = when {
                        (showPassword) -> painterResource(id = R.drawable.outline_visibility_24)
                        else -> painterResource(id = R.drawable.outline_visibility_off_24)
                    },
                    contentDescription = "Show password clickable icon"
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = label,
            )
        },
    )

}