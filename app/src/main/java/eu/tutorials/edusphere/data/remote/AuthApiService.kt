package eu.tutorials.edusphere.data.remote

import eu.tutorials.edusphere.data.request.LoginRequest
import eu.tutorials.edusphere.data.request.RefreshTokenRequest
import eu.tutorials.edusphere.data.request.RegisterRequest
import eu.tutorials.edusphere.data.response.AuthResponse
import eu.tutorials.edusphere.data.response.RefreshTokenResponse
import eu.tutorials.edusphere.domain.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


class AuthApiService(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "http://192.168.31.171:8080"
    }

    suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    suspend fun register(registerRequest: RegisterRequest): AuthResponse {
        return client.post("$BASE_URL/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }.body()
    }

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse {
        return client.post("$BASE_URL/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(refreshTokenRequest)
        }.body()
    }

    suspend fun logout(): Boolean {
        return try {
            client.post("$BASE_URL/auth/logout") {
                contentType(ContentType.Application.Json)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCurrentUser(): User {
        return client.get("$BASE_URL/auth/me") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}