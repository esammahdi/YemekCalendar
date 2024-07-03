package com.esammahdi.yemekcalendar.authentication.presentation.viewmodel

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.authentication.data.interfaces.AuthRepository
import com.esammahdi.yemekcalendar.core.data.interfaces.LocalUserRepository
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
class ResetPasswordViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val repository: AuthRepository,
    private val localUserRepository: LocalUserRepository
) : ViewModel() {
    private val _resetPasswordState = MutableStateFlow(ResetPasswordState())
    val resetPasswordState: StateFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()


    private var initialized = false

    @MainThread
    fun initialize() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            val language = localUserRepository.getLanguageChoice()
            _resetPasswordState.value = _resetPasswordState.value.copy(selectedLanguage = language)
        }
    }

    val res = appContext.resources

    val emptyEmailErrorMessage = res.getString(R.string.empty_email_error_message)
    val invalidEmailErrorMessage = res.getString(R.string.invalid_email_error_message)
    val validationErrorMessage = res.getString(R.string.validation_error_message)

    fun onEmailValueChanged(email: String) {
        _resetPasswordState.value = _resetPasswordState.value.copy(
            email = email,
            emailErrorMessage = when {
                email.isEmpty() -> emptyEmailErrorMessage
                !isValidEmail(email) -> invalidEmailErrorMessage
                else -> ""
            }
        )
    }

    fun onResetButtonClicked() {
        if (_resetPasswordState.value.emailErrorMessage.isEmpty()) {
            resetPassword(_resetPasswordState.value.email)
        } else {
            _resetPasswordState.value = _resetPasswordState.value.copy(
                isError = true,
                errorMessage = validationErrorMessage
            )
        }
    }

    fun onLanguageSelected(language: Languages) {
        viewModelScope.launch {
            localUserRepository.saveLanguageChoice(language)
            _resetPasswordState.value = _resetPasswordState.value.copy(selectedLanguage = language)
        }
    }

    private fun resetPassword(email: String) = viewModelScope.launch {
        _resetPasswordState.value = _resetPasswordState.value.copy(isLoading = true)
        repository.resetPassword(email)
        _resetPasswordState.value =
            _resetPasswordState.value.copy(isLoading = false, isSuccess = true)
    }

    fun onResetPasswordDialogDismiss() {
        _resetPasswordState.value = _resetPasswordState.value.copy(isSuccess = false)
    }
}

data class ResetPasswordState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val emailErrorMessage: String = "",
    val selectedLanguage: Languages = Languages.ENGLISH
)