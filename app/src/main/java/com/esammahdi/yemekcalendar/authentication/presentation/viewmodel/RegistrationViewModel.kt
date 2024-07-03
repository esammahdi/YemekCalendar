package com.esammahdi.yemekcalendar.authentication.presentation.viewmodel

import android.content.Context
import android.content.res.Resources
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.authentication.data.interfaces.AuthRepository
import com.esammahdi.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import com.esammahdi.yemekcalendar.core.other.utils.isValidEmail
import com.esammahdi.yemekcalendar.settings.presentation.viewmodel.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: AuthRepository,
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()

    private var initialized = false

    @MainThread
    fun initialize() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            val language = localUserRepository.getLanguageChoice()
            _registrationState.value = _registrationState.value.copy(selectedLanguage = language)
        }
    }

    val res: Resources = appContext.resources

    private val emptyEmailErrorMessage = res.getString(R.string.empty_email_error_message)
    private val emptyPasswordErrorMessage = res.getString(R.string.empty_password_error_message)
    private val invalidEmailErrorMessage = res.getString(R.string.invalid_email_error_message)
    private val invalidPasswordErrorMessage = res.getString(R.string.invalid_password_error_message)
    private val emailMismatchErrorMessage = res.getString(R.string.email_mismatch_error_message)
    private val passwordMismatchErrorMessage =
        res.getString(R.string.password_mismatch_error_message)
    private val emptyRepeatEmailErrorMessage =
        res.getString(R.string.empty_repeat_email_error_message)
    private val emptyRepeatPasswordErrorMessage =
        res.getString(R.string.empty_repeat_password_error_message)
    private val validationErrorMessage = res.getString(R.string.validation_error_message)


    fun onEmailValueChanged(email: String) {
        _registrationState.value = _registrationState.value.copy(
            email = email,
            emailErrorMessage = when {
                email.isEmpty() -> emptyEmailErrorMessage
                !isValidEmail(email) -> invalidEmailErrorMessage
                else -> ""
            }
        )
    }

    fun onRepeatEmailValueChanged(repeatEmail: String) {
        _registrationState.value = _registrationState.value.copy(
            repeatEmail = repeatEmail,
            repeatEmailErrorMessage = when {
                repeatEmail.isEmpty() -> emptyRepeatEmailErrorMessage
                !repeatEmail.equals(
                    _registrationState.value.email,
                    ignoreCase = true
                ) -> emailMismatchErrorMessage

                else -> ""
            }
        )
    }

    fun onPasswordValueChanged(password: String) {
        _registrationState.value = _registrationState.value.copy(
            password = password,
            passwordErrorMessage = when {
                password.isEmpty() -> emptyPasswordErrorMessage
                password.length < 8 -> invalidPasswordErrorMessage
                else -> ""
            }
        )
    }

    fun onRepeatPasswordValueChanged(repeatPassword: String) {
        _registrationState.value = _registrationState.value.copy(
            repeatPassword = repeatPassword,
            repeatPasswordErrorMessage = when {
                repeatPassword.isEmpty() -> emptyRepeatPasswordErrorMessage
                repeatPassword != _registrationState.value.password -> passwordMismatchErrorMessage
                else -> ""
            }
        )
    }

    fun onShowPasswordClicked() {
        _registrationState.value = _registrationState.value.copy(
            showPassword = _registrationState.value.showPassword.not()
        )
    }


    fun onRegisterButtonClicked() {
        if (_registrationState.value.emailErrorMessage.isEmpty() &&
            _registrationState.value.passwordErrorMessage.isEmpty() &&
            _registrationState.value.repeatEmailErrorMessage.isEmpty() &&
            _registrationState.value.repeatPasswordErrorMessage.isEmpty()
        ) {
            registerUser(_registrationState.value.email, _registrationState.value.password)
        } else {
            _registrationState.value = _registrationState.value.copy(
                isError = true,
                errorMessage = validationErrorMessage
            )
        }
    }


    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        repository.registerUser(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _registrationState.value = _registrationState.value.copy(
                        isSuccess = true,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    _registrationState.value = _registrationState.value.copy(
                        isLoading = true,
                        isError = false
                    )
                }

                is Resource.Error -> {
                    _registrationState.value = _registrationState.value.copy(
                        isError = true,
                        isLoading = false,
                        errorMessage = result.message
                    )

                }
            }
        }
    }

    fun onRegistrationSuccessDialogDismiss() {
        _registrationState.value = _registrationState.value.copy(
            isSuccess = false
        )
    }

    fun onLanguageSelected(language: Languages) {
        viewModelScope.launch {
            localUserRepository.saveLanguageChoice(language)
            _registrationState.value = _registrationState.value.copy(selectedLanguage = language)
        }
    }

}

data class RegistrationState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val password: String = "",
    val repeatEmail: String = "",
    val repeatPassword: String = "",
    val emailErrorMessage: String = "",
    val passwordErrorMessage: String = "",
    val repeatEmailErrorMessage: String = "",
    val repeatPasswordErrorMessage: String = "",
    val selectedLanguage: Languages = Languages.ENGLISH,
    val showPassword: Boolean = false,
)