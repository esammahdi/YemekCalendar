package com.example.yemekcalendar.authentication.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.R

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    emailErrorMessage: String,
    label: String = stringResource(R.string.email),
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done
) {
    TextField(
        label = { Text(label) },
        value = email,
        onValueChange = { onValueChange(it) },
        isError = emailErrorMessage.isNotEmpty(),
        modifier = modifier,
//        colors = TextFieldDefaults.colors().copy(
//            cursorColor = Color.Black,
//            disabledLabelColor = lightBlue,
//            unfocusedIndicatorColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Email Icon",
            )
        },
        supportingText = {
            if (emailErrorMessage.isNotEmpty()) {
                Text(
                    text = emailErrorMessage,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = label,
            )
        }
    )
}