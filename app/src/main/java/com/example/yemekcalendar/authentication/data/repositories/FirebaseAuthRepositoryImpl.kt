package com.example.yemekcalendar.authentication.data.repositories

import com.example.yemekcalendar.authentication.data.interfaces.AuthRepository
import com.example.yemekcalendar.core.other.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }

    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
//        return flow{
//            emit(Resource.Loading())
//            val result = firebaseAuth.sendPasswordResetEmail(email).await()
//            emit(Resource.Success(result))
//        }.catch {
//            emit(Resource.Error(it.message.toString()))
//        }
    }

    override fun logoutUser() {
        firebaseAuth.signOut()
//        return flow {
//            emit(Resource.Loading())
//            firebaseAuth.signOut()
//            emit(Resource.Success(AuthResult(Unit, null)))
//        }.catch {
//            emit(Resource.Error(it.message.toString()))
//        }
    }


}