package eu.tutorials.edusphere.domain.useCase

import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.User
import eu.tutorials.edusphere.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthResult<User> {
        return if (email.isBlank() || password.isBlank()) {
            AuthResult.Error("Email and password cannot be empty")
        } else {
            repository.login(email, password)
        }
    }
}
