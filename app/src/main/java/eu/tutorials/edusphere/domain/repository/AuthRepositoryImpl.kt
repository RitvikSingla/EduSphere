package eu.tutorials.edusphere.domain.repository

import android.util.Log
import eu.tutorials.edusphere.data.remote.AuthApiService
import eu.tutorials.edusphere.data.request.LoginRequest
import eu.tutorials.edusphere.data.request.RegisterRequest
import eu.tutorials.edusphere.data.util.TokenPair
import eu.tutorials.edusphere.data.util.TokenStorage
import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult<TokenPair> {
        return authApiService.login(LoginRequest(email, password))
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: String
    ): AuthResult<TokenPair> {
        return authApiService.register(RegisterRequest(name, email, password, role))
    }

    override suspend fun refreshToken(): AuthResult<TokenPair> {
        return authApiService.refreshToken()
    }

    override suspend fun logout(){
        authApiService.logout()
    }

    override suspend fun getCurrentUser(): User? {
        TODO("Not yet implemented")
    }

//    override suspend fun getCurrentUser(): User? {
//        return try {
//            val accessToken = tokenManager.getAccessToken()
//            if (accessToken != null) {
//                authApiService.getCurrentUser()
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            null
//        }
//    }

    override fun isLoggedIn(): Flow<Boolean> {
        return tokenStorage.isLoggedIn()
    }
}