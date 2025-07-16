package eu.tutorials.edusphere.domain.repository

import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.Role
import eu.tutorials.edusphere.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult<User>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: Role
    ): AuthResult<User>
    suspend fun refreshToken(): AuthResult<Boolean>
    suspend fun logout(): AuthResult<Boolean>
    suspend fun getCurrentUser(): User?
    suspend fun isLoggedIn(): Boolean
}