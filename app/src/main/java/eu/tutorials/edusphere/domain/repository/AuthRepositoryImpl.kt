package eu.tutorials.edusphere.domain.repository

import android.util.Log
import eu.tutorials.edusphere.data.remote.AuthApiService
import eu.tutorials.edusphere.data.request.LoginRequest
import eu.tutorials.edusphere.data.request.RefreshTokenRequest
import eu.tutorials.edusphere.data.request.RegisterRequest
import eu.tutorials.edusphere.data.response.TokenPairResponse
import eu.tutorials.edusphere.data.util.TokenManager
import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.Role
import eu.tutorials.edusphere.domain.model.User
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.client.plugins.*
import io.ktor.http.*
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): AuthResult<User> {
        return try {
            val loginRequest = LoginRequest(email, password)
            val response = authApiService.login(loginRequest)

            // Save tokens
            tokenManager.saveTokens(response.accessToken, response.refreshToken)

            AuthResult.Success(response.user)
        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> AuthResult.Error("Invalid email or password")
                HttpStatusCode.BadRequest -> AuthResult.Error("Please check your input")
                else -> AuthResult.Error("Login failed: ${e.response.status}")
            }
        } catch (e: ServerResponseException) {
            AuthResult.Error("Server error occurred. Please try again later.")
        } catch (e: Exception) {
            AuthResult.Error("Network error: ${e.message}", e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: Role
    ): AuthResult<User> {
        return try {
            Log.d("Register", "Calling register API...")
            Log.d("Register", "Register success: ${TokenPairResponse}Response")
            val registerRequest = RegisterRequest(name, email, password, role.name)
            val response = authApiService.register(registerRequest)
            println("Register success: $response")
            // Save tokens
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            AuthResult.Success(response.user)
        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Conflict -> AuthResult.Error("Email already exists")
                HttpStatusCode.BadRequest -> AuthResult.Error("Please check your input")
                else -> AuthResult.Error("Registration failed: ${e.response.status}")
            }
        } catch (e: ServerResponseException) {
            AuthResult.Error("Server error occurred. Please try again later.")
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.Error("Network error: ${e.message}", e)
        }
    }

    override suspend fun refreshToken(): AuthResult<Boolean> {
        return try {
            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken == null) {
                return AuthResult.Error("No refresh token available")
            }

            val refreshRequest = RefreshTokenRequest(refreshToken)
            val response = authApiService.refreshToken(refreshRequest)

            // Save new tokens
            tokenManager.saveTokens(response.accessToken, response.refreshToken)

            AuthResult.Success(true)
        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> {
                    // Clear tokens if refresh fails
                    tokenManager.clearTokens()
                    AuthResult.Error("Session expired. Please login again.")
                }
                else -> AuthResult.Error("Token refresh failed: ${e.response.status}")
            }
        } catch (e: ServerResponseException) {
            AuthResult.Error("Server error occurred. Please try again later.")
        } catch (e: Exception) {
            AuthResult.Error("Network error: ${e.message}", e)
        }
    }

    override suspend fun logout(): AuthResult<Boolean> {
        return try {
            // Call logout API
            val success = authApiService.logout()

            // Clear tokens regardless of API response
            tokenManager.clearTokens()

            AuthResult.Success(success)
        } catch (e: Exception) {
            // Clear tokens even if API call fails
            tokenManager.clearTokens()
            AuthResult.Success(true) // Return success since tokens are cleared
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            val accessToken = tokenManager.getAccessToken()
            if (accessToken != null) {
                authApiService.getCurrentUser()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return try {
            val accessToken = tokenManager.getAccessToken()
            !accessToken.isNullOrEmpty()
        } catch (e: Exception) {
            false
        }
    }
}