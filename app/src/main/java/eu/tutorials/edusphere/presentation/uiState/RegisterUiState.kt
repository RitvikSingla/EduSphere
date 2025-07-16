package eu.tutorials.edusphere.presentation.uiState

import eu.tutorials.edusphere.domain.model.Role

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val selectedRole: Role = Role.STUDENT,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRegistrationSuccessful: Boolean = false
)