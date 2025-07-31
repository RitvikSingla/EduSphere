package eu.tutorials.edusphere.di

import android.R.attr.level
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.edusphere.data.request.RefreshRequest
import eu.tutorials.edusphere.data.util.TokenPair
import eu.tutorials.edusphere.data.util.TokenStorage
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(tokenStorage: TokenStorage): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }

            install(Logging) {
                level = LogLevel.INFO
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenStorage.getAccessToken()
                        if (accessToken != null) {
                            BearerTokens(accessToken, "")
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val refreshToken = tokenStorage.getRefreshToken()
                        if (refreshToken != null) {
                            val response = client.post("http://10.0.2.2:8080/auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(RefreshRequest(refreshToken))
                            }

                            if (response.status == HttpStatusCode.OK) {
                                val tokenPair = response.body<TokenPair>()
                                tokenStorage.saveTokens(tokenPair)
                                BearerTokens(tokenPair.accessToken, tokenPair.refreshToken)
                            } else {
                                tokenStorage.clearTokens()
                                null
                            }
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }
}