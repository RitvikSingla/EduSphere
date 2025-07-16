package eu.tutorials.edusphere.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.edusphere.domain.model.AuthResult
import eu.tutorials.edusphere.domain.model.Role
import eu.tutorials.edusphere.domain.useCase.LoginUseCase
import eu.tutorials.edusphere.domain.useCase.RegisterUseCase
import eu.tutorials.edusphere.presentation.uiState.LoginUiState
import eu.tutorials.edusphere.presentation.uiState.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState: StateFlow<RegisterUiState> = _registerState.asStateFlow()

    // Login Functions
    fun updateLoginEmail(email: String) {
        _loginState.value = _loginState.value.copy(email = email, error = null)
    }

    fun updateLoginPassword(password: String) {
        _loginState.value = _loginState.value.copy(password = password, error = null)
    }

    fun login() {
        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(isLoading = true, error = null)

            when (val result = loginUseCase(_loginState.value.email, _loginState.value.password)) {
                is AuthResult.Success -> {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                }
                is AuthResult.Error -> {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is AuthResult.Loading -> {
                    _loginState.value = _loginState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun clearLoginError() {
        _loginState.value = _loginState.value.copy(error = null)
    }

    // Register Functions
    fun updateRegisterName(name: String) {
        _registerState.value = _registerState.value.copy(name = name, error = null)
    }

    fun updateRegisterEmail(email: String) {
        _registerState.value = _registerState.value.copy(email = email, error = null)
    }

    fun updateRegisterPassword(password: String) {
        _registerState.value = _registerState.value.copy(password = password, error = null)
    }

    fun updateRole(role: Role) {
        _registerState.value = _registerState.value.copy(selectedRole = role, error = null)
    }

    fun register() {
        viewModelScope.launch {
            _registerState.value = _registerState.value.copy(isLoading = true, error = null)

            when (val result = registerUseCase(
                _registerState.value.name,
                _registerState.value.email,
                _registerState.value.password,
                _registerState.value.selectedRole
            )) {
                is AuthResult.Success -> {
                    _registerState.value = _registerState.value.copy(
                        isLoading = false,
                        isRegistrationSuccessful = true
                    )
                }
                is AuthResult.Error -> {
                    _registerState.value = _registerState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
                is AuthResult.Loading -> {
                    _registerState.value = _registerState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun clearRegisterError() {
        _registerState.value = _registerState.value.copy(error = null)
    }

    // Reset states when navigating between screens
    fun resetLoginState() {
        _loginState.value = LoginUiState()
    }

    fun resetRegisterState() {
        _registerState.value = RegisterUiState()
    }
}