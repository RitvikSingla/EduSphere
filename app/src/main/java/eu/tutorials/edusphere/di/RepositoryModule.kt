package eu.tutorials.edusphere.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.tutorials.edusphere.domain.repository.AuthRepository
import eu.tutorials.edusphere.domain.repository.AuthRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}