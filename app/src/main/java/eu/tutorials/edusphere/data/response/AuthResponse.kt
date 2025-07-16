package eu.tutorials.edusphere.data.response

import eu.tutorials.edusphere.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long
)