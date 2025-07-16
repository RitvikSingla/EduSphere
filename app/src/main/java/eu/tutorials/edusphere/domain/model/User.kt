package eu.tutorials.edusphere.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: Role,
    val createdAt: String,
    val updatedAt: String
)
