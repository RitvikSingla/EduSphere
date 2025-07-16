package eu.tutorials.edusphere.domain.model

import io.ktor.http.HttpStatusCode

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val message: String, val exception: Throwable? = null) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}