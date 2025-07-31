package eu.tutorials.edusphere.data.util

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//class KtorClient(private val tokenManager: TokenManager) {
//
//    companion object {
//        private const val TAG = "KtorClient"
//    }
//
//    fun create(): HttpClient {
//        return HttpClient(Android) {
//            // JSON Configuration
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                    isLenient = true
//                    encodeDefaults = false
//                })
//            }
//
//            // Logging
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
//
//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        try {
//                            val accessToken = tokenManager.getAccessToken()
//                            val refreshToken = tokenManager.getRefreshToken()
//
//                            Log.d(TAG, "Loading tokens - Access: ${accessToken != null}, Refresh: ${refreshToken != null}")
//
//                            if (accessToken != null && refreshToken != null) {
//                                BearerTokens(accessToken, refreshToken)
//                            } else {
//                                null
//                            }
//                        } catch (e: Exception) {
//                            Log.e(TAG, "Error loading tokens: ${e.message}", e)
//                            null
//                        }
//                    }
//
//                    refreshTokens {
//                        val refreshToken = tokenManager.getRefreshToken()
//                        if (refreshToken != null) {
//                            Log.d(TAG, "Attempting token refresh")
//                            null // Return new tokens or null if refresh fails
//                        } else {
//                            Log.w(TAG, "No refresh token available")
//                            null
//                        }
//                    }
//                }
//            }
//
//            // Default Request Configuration
//            install(DefaultRequest) {
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                // Add ngrok header to skip browser warning
//                header("ngrok-skip-browser-warning", "true")
//                header(HttpHeaders.UserAgent, "EduSphere-Android-App")
//            }
//
//            // Timeout Configuration
//            install(HttpTimeout) {
//                requestTimeoutMillis = 60000
//                connectTimeoutMillis = 60000
//                socketTimeoutMillis = 60000
//            }
//        }
//    }
//
//    // Client with Bearer Token Authentication
//    fun createWithAuth(token: String): HttpClient {
//        return HttpClient(Android) {
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                    isLenient = true
//                    encodeDefaults = false
//                })
//            }
//
//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        BearerTokens(token, token)
//                    }
//                }
//            }
//
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
//
//            install(DefaultRequest) {
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//                header("ngrok-skip-browser-warning", "true")
//                header(HttpHeaders.UserAgent, "EduSphere-Android-App")
//            }
//
//            install(HttpTimeout) {
//                requestTimeoutMillis = 60000
//                connectTimeoutMillis = 60000
//                socketTimeoutMillis = 60000
//            }
//        }
//    }
//}