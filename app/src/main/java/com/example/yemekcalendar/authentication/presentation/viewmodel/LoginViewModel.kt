package com.example.yemekcalendar.authentication.presentation.viewmodel

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yemekcalendar.R
import com.example.yemekcalendar.authentication.data.interfaces.AuthRepository
import com.example.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.example.yemekcalendar.core.other.utils.Resource
import com.example.yemekcalendar.core.other.utils.isValidEmail
import com.example.yemekcalendar.settings.presentation.viewmodel.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val repository: AuthRepository,
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private var initialized = false

    @MainThread
    fun initialize() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            val language = localUserRepository.getLanguageChoice()
            _loginState.value = _loginState.value.copy(selectedLanguage = language)
        }
    }

    val resources = appContext.resources
    val emptyEmailErrorMessage = resources.getString(R.string.empty_email_error_message)
    val emptyPasswordErrorMessage = resources.getString(R.string.empty_password_error_message)
    val invalidEmailErrorMessage = resources.getString(R.string.invalid_email_error_message)
    val invalidPasswordErrorMessage = resources.getString(R.string.invalid_password_error_message)
    val validationErrorMessage = resources.getString(R.string.validation_error_message)

    fun onEmailValueChanged(email: String) {
        _loginState.value = _loginState.value.copy(
            email = email,
            emailErrorMessage = when {
                email.isEmpty() -> emptyEmailErrorMessage
                !isValidEmail(email) -> invalidEmailErrorMessage
                else -> ""
            }
        )
    }

    fun onPasswordValueChanged(password: String) {
        _loginState.value = _loginState.value.copy(
            password = password,
            passwordErrorMessage = when {
                password.isEmpty() -> emptyPasswordErrorMessage
                password.length < 8 -> invalidPasswordErrorMessage
                else -> ""
            }
        )
    }

    fun onShowPasswordClicked() {
        _loginState.value = _loginState.value.copy(
            showPassword = _loginState.value.showPassword.not()
        )
    }

    fun onRememberUserChanged(rememberUser: Boolean) {
        _loginState.value = _loginState.value.copy(
            rememberUser = rememberUser
        )
    }

    fun onLoginButtonClicked() {
        if (_loginState.value.emailErrorMessage.isEmpty() && _loginState.value.passwordErrorMessage.isEmpty()) {
            loginUser(_loginState.value.email, _loginState.value.password)
        } else {
            _loginState.value = _loginState.value.copy(
                isError = true,
                errorMessage = validationErrorMessage
            )
        }
    }


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    localUserRepository.saveTemporaryUser(email)

                    if (_loginState.value.rememberUser) {
                        localUserRepository.saveUser(email)
                    }

                    _loginState.value = _loginState.value.copy(
                        isSuccess = true,
                        isLoading = false
                    )

                }

                is Resource.Loading -> {
                    _loginState.value = _loginState.value.copy(
                        isLoading = true,
                        isError = false,
                        isSuccess = false
                    )
                }

                is Resource.Error -> {
                    _loginState.value = _loginState.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = result.message
                    )

                }
            }
        }
    }

    fun onLanguageSelected(language: Languages) {
        viewModelScope.launch {
            localUserRepository.saveLanguageChoice(language)
            _loginState.value = _loginState.value.copy(selectedLanguage = language)
        }
    }

}

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val password: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val showPassword: Boolean = false,
    val rememberUser: Boolean = false,
    val selectedLanguage: Languages = Languages.ENGLISH
)