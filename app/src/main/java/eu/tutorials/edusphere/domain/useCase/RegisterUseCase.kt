package eu.tutorials.edusphere.domain.useCase


import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.Role
import eu.tutorials.edusphere.domain.model.User
import eu.tutorials.edusphere.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        role: Role
    ): AuthResult<User> {
        return when {
            name.isBlank() -> AuthResult.Error("Name cannot be empty")
            email.isBlank() -> AuthResult.Error("Email cannot be empty")
            !isValidEmail(email) -> AuthResult.Error("Invalid email format")
            password.length < 9 -> AuthResult.Error("Password must be at least 9 characters")
            !isValidPassword(password) -> AuthResult.Error("Password must contain at least one digit, uppercase and lowercase letter")
            else -> {
                try {
                    repository.register(name, email, password, role)
                } catch (e: Exception) {
                    e.printStackTrace() // Optional: helpful for debugging
                    AuthResult.Error(e.localizedMessage ?: "An unknown error occurred")
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$".toRegex()
        return regex.matches(password)
    }
}
