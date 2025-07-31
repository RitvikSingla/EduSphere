package eu.tutorials.edusphere.domain.model

import io.ktor.http.HttpStatusCode

sealed class AuthResult<T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error<T>(val message: String) : AuthResult<T>()
    data class Loading<T>(val isLoading: Boolean = true) : AuthResult<T>()
}