package com.example.yemekcalendar.core.data.repositories

import android.net.Uri
import com.example.yemekcalendar.core.data.interfaces.OnlineStorageRepository
import com.example.yemekcalendar.core.other.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseOnlineStorageRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
) : OnlineStorageRepository {
    override suspend fun getProfilePictureUrl(): String? {
        val user = firebaseAuth.currentUser
        return user?.photoUrl?.toString()
    }

    override suspend fun changeProfilePicture(uri: Uri): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            // Upload the profile picture
            val storageRef = firebaseStorage.reference
            val profilePicsRef = storageRef.child("profile_pictures/${UUID.randomUUID()}.jpg")
            val uploadTask = profilePicsRef.putFile(uri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

            // Update the user profile
            val user = FirebaseAuth.getInstance().currentUser
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(downloadUrl))
                .build()

            user?.updateProfile(profileUpdates)?.await()

            emit(Resource.Success(downloadUrl))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

}