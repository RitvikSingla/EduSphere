package eu.tutorials.edusphere.data.remote

import android.util.Log
import eu.tutorials.edusphere.data.request.LoginRequest
import eu.tutorials.edusphere.data.request.RefreshRequest
import eu.tutorials.edusphere.data.request.RefreshTokenRequest
import eu.tutorials.edusphere.data.request.RegisterRequest
import eu.tutorials.edusphere.data.response.AuthResponse
import eu.tutorials.edusphere.data.response.RefreshTokenResponse
import eu.tutorials.edusphere.data.util.ApiError
import eu.tutorials.edusphere.data.util.TokenPair
import eu.tutorials.edusphere.data.util.TokenStorage
import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthApiService @Inject constructor(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) {

    companion object {
        private const val BASE_URL = "http://192.168.47.82:8080"
        private const val AUTH_ENDPOINT = "$BASE_URL/auth"
    }

    suspend fun register(registerRequest: RegisterRequest): AuthResult<TokenPair> {
        return try {
            val response = httpClient.post("$AUTH_ENDPOINT/register") {
                contentType(ContentType.Application.Json)
                setBody(registerRequest)
            }

            when(response.status){
                HttpStatusCode.OK -> {
                    val tokenPair = response.body<TokenPair>()
                    tokenStorage.saveTokens(tokenPair)
                    AuthResult.Success(tokenPair)
                }
                else -> {
                    val error = response.body<ApiError>()
                    AuthResult.Error(error.message)
                }
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Registration failed")
        }
    }

    suspend fun login(loginRequest: LoginRequest): AuthResult<TokenPair> {
        return try {
            val response = httpClient.post("$AUTH_ENDPOINT/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    val tokenPair = response.body<TokenPair>()
                    tokenStorage.saveTokens(tokenPair)
                    AuthResult.Success(tokenPair)
                }
                else -> {
                    val error = response.body<ApiError>()
                    AuthResult.Error(error.message)
                }
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    suspend fun refreshToken(): AuthResult<TokenPair> {
        return try {
            val refreshToken = tokenStorage.getRefreshToken()
                ?: return AuthResult.Error("No refresh token available")

            val response = httpClient.post("$AUTH_ENDPOINT/refresh") {
                contentType(ContentType.Application.Json)
                setBody(RefreshRequest(refreshToken))
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    val tokenPair = response.body<TokenPair>()
                    tokenStorage.saveTokens(tokenPair)
                    AuthResult.Success(tokenPair)
                }
                else -> {
                    tokenStorage.clearTokens()
                    AuthResult.Error("Token refresh failed")
                }
            }
        } catch (e: Exception) {
            tokenStorage.clearTokens()
            AuthResult.Error(e.message ?: "Token refresh failed")
        }
    }

    suspend fun logout() {
        tokenStorage.clearTokens()
    }
}