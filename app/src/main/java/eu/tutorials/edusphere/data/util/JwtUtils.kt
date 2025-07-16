package eu.tutorials.edusphere.data.util

import android.util.Base64
import kotlinx.serialization.json.Json
import javax.inject.Inject

class JwtUtils @Inject constructor() {
    fun getUserIdFromToken(token: String): String {
        val cleanToken = token.removePrefix("Bearer ").trim()
        val payload = decodeJwtPayload(cleanToken)
        return payload["sub"] as? String ?: throw IllegalArgumentException("Invalid Token")
    }

    fun getUserRoleFromToken(token: String) : String {
        val cleanToken = token.removePrefix("Bearer ").trim()
        val payload = decodeJwtPayload(cleanToken)
        return payload["role"] as? String ?: throw IllegalArgumentException("Invalid Token")
    }

    private fun decodeJwtPayload(token: String): Map<String, Any> {
        val parts = token.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid JWT token")

        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
        val json = String(decodedBytes)

        return Json.decodeFromString(json)
    }
}