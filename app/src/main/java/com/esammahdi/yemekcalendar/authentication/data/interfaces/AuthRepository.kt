package com.esammahdi.yemekcalendar.authentication.data.interfaces

import com.esammahdi.yemekcalendar.core.other.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun resetPassword(email: String)
    fun logoutUser()
}