package eu.tutorials.edusphere.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.tutorials.edusphere.domain.viewModel.AuthViewModel
import eu.tutorials.edusphere.presentation.components.AuthButton
import eu.tutorials.edusphere.presentation.components.AuthTextField
import eu.tutorials.edusphere.presentation.components.PasswordTextField
import eu.tutorials.edusphere.presentation.components.RoleSelector

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.registerState.collectAsState()

    LaunchedEffect(uiState.isRegistrationSuccessful) {
        if (uiState.isRegistrationSuccessful) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo/Brand
        Text(
            text = "EduSphere",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Create your account to get started with learning.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Name Field
        AuthTextField(
            value = uiState.name,
            onValueChange = viewModel::updateRegisterName,
            label = "Full Name",
            modifier = Modifier.padding(bottom = 16.dp),
            isError = uiState.error != null,
            keyboardType = KeyboardType.Text,
            enabled = !uiState.isLoading
        )

        // Email Field
        AuthTextField(
            value = uiState.email,
            onValueChange = viewModel::updateRegisterEmail,
            label = "Email",
            modifier = Modifier.padding(bottom = 16.dp),
            isError = uiState.error != null,
            keyboardType = KeyboardType.Email,
            enabled = !uiState.isLoading
        )

        // Password Field
        PasswordTextField(
            value = uiState.password,
            onValueChange = viewModel::updateRegisterPassword,
            label = "Password",
            modifier = Modifier.padding(bottom = 16.dp),
            isError = uiState.error != null,
            enabled = !uiState.isLoading
        )

        // Role Selector
        RoleSelector(
            selectedRole = uiState.selectedRole,
            onRoleSelected = viewModel::updateRole,
            modifier = Modifier.padding(bottom = 24.dp),
            enabled = !uiState.isLoading
        )

        // Error Message
        if (uiState.error != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = uiState.error ?: "",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Register Button
        AuthButton(
            text = "Sign Up",
            onClick = viewModel::register,
            modifier = Modifier.padding(bottom = 16.dp),
            enabled = uiState.name.isNotBlank() &&
                    uiState.email.isNotBlank() &&
                    uiState.password.isNotBlank(),
            isLoading = uiState.isLoading
        )

        // Login Link
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }
    }
}