package com.esammahdi.yemekcalendar.settings.presentation.viewmodel

import android.net.Uri
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esammahdi.yemekcalendar.core.data.interfaces.LocalUserRepository
import com.esammahdi.yemekcalendar.core.data.interfaces.OnlineStorageRepository
import com.esammahdi.yemekcalendar.core.other.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localUserRepository: LocalUserRepository,
    private val onlineStorageRepository: OnlineStorageRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()
    private var initializeCalled = false

    // This function is idempotent provided it is only called from the UI thread.
    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true

        viewModelScope.launch {
            loadUserEmail()
            loadProfilePictureDownloadUrlFromOnlineStorage()
            loadProfilePictureDownloadUrl()
            _profileState.value = _profileState.value.copy(
                isLoading = false
            )
        }
    }

    private suspend fun loadUserEmail() {
        val isUserRemembered = localUserRepository.isUserSaved()

        val email = if (isUserRemembered) {
            localUserRepository.getSavedUser()
        } else {
            localUserRepository.getTemporaryUser()
        }
        _profileState.value = _profileState.value.copy(email = email)
    }

    private suspend fun loadProfilePictureDownloadUrlFromOnlineStorage() {
        val url = onlineStorageRepository.getProfilePictureUrl()
        localUserRepository.saveProfilePictureDownloadUrl(url ?: "", _profileState.value.email)
        _profileState.value = _profileState.value.copy(profilePictureDownloadUrl = url)
    }

    private suspend fun loadProfilePictureDownloadUrl() = viewModelScope.launch {
        val url = localUserRepository.getProfilePictureDownloadUrl(_profileState.value.email)
        _profileState.value = _profileState.value.copy(profilePictureDownloadUrl = url)
    }

    fun changeProfilePicture(uri: Uri) = viewModelScope.launch {
        onlineStorageRepository.changeProfilePicture(uri).collectLatest { result ->
            when (result) {
                is Resource.Error -> {
                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        isProfilePictureChangeError = true,
                        profilePictureChangeErrorMessage = result.message
                            ?: "An unknown error occured while changin the profile picture"
                    )
                }

                is Resource.Loading -> {
                    _profileState.value = _profileState.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {

                    localUserRepository.saveProfilePictureDownloadUrl(
                        url = result.data!!,
                        email = _profileState.value.email
                    )

                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        isProfilePictureChangeSuccess = true,
                        profilePictureDownloadUrl = result.data
                    )

                }
            }
        }
    }

    fun resetProfilePictureChangeState() {
        _profileState.value = _profileState.value.copy(
            isProfilePictureChangeSuccess = false,
            isProfilePictureChangeError = false,
            profilePictureChangeErrorMessage = ""
        )
    }

    fun onLogoutButtonClicked() {
        viewModelScope.launch {
            localUserRepository.deleteSavedUser()
            _profileState.value = _profileState.value.copy(isLoggoedOut = true)
        }
    }

    fun onRefresh() {
        _profileState.value = _profileState.value.copy(isRefreshing = true)
        viewModelScope.launch {
            loadProfilePictureDownloadUrlFromOnlineStorage()
        }
        _profileState.value = _profileState.value.copy(isRefreshing = false)
    }
}

data class ProfileState(
    val email: String = "",
    val profilePictureDownloadUrl: String? = null,
    val isProfilePictureChangeSuccess: Boolean = false,
    val isProfilePictureChangeError: Boolean = false,
    val profilePictureChangeErrorMessage: String = "",
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isLoggoedOut: Boolean = false
)