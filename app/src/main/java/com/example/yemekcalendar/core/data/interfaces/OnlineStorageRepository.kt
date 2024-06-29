package com.example.yemekcalendar.core.data.interfaces

import android.net.Uri
import com.example.yemekcalendar.core.other.utils.Resource
import kotlinx.coroutines.flow.Flow

interface OnlineStorageRepository {
    suspend fun getProfilePictureUrl(): String?

    suspend fun changeProfilePicture(uri: Uri): Flow<Resource<String>>

}