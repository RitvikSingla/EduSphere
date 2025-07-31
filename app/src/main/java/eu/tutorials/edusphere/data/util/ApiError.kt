package eu.tutorials.edusphere.data.util

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val message: String,
    val status: Int
)
