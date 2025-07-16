package eu.tutorials.edusphere.di

import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.tutorials.edusphere.data.remote.AuthApiService
import eu.tutorials.edusphere.data.util.KtorClient
import eu.tutorials.edusphere.data.util.TokenManager
import eu.tutorials.edusphere.domain.repository.AuthRepository
import eu.tutorials.edusphere.domain.repository.AuthRepositoryImpl
import io.ktor.client.HttpClient
import android.content.Context
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideHttpClientFactory(tokenManager: TokenManager): KtorClient {
        return KtorClient(tokenManager)
    }

    @Provides
    @Singleton
    fun provideHttpClient(ktorClient: KtorClient): HttpClient {
        return ktorClient.create()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(client: HttpClient): AuthApiService {
        return AuthApiService(client)
    }

    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(authApiService, tokenManager)
    }
}