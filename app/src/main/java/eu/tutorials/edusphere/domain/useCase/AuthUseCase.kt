package eu.tutorials.edusphere.domain.useCase

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val login: LoginUseCase,
    val register: RegisterUseCase
)
