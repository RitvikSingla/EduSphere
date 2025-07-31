package eu.tutorials.edusphere.domain.repository

import eu.tutorials.edusphere.data.util.TokenPair
import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<TokenPair>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: String
    ): AuthResult<TokenPair>
    suspend fun refreshToken(): AuthResult<TokenPair>
    suspend fun logout()
    suspend fun getCurrentUser(): User?
    fun isLoggedIn(): Flow<Boolean>
}