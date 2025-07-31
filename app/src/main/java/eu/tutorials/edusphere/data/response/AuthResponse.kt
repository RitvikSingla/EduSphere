package eu.tutorials.edusphere.data.response

import eu.tutorials.edusphere.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class AuthResponse(
    val user: UserDto,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    @SerialName("expiresin")
    val expiresIn: Long
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)