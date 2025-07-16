package eu.tutorials.edusphere.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.loginState.collectAsState()

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
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
            text = "Welcome back! Please sign in to your account.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email Field
        AuthTextField(
            value = uiState.email,
            onValueChange = viewModel::updateLoginEmail,
            label = "Email",
            modifier = Modifier.padding(bottom = 16.dp),
            isError = uiState.error != null,
            keyboardType = KeyboardType.Email,
            enabled = !uiState.isLoading
        )

        // Password Field
        PasswordTextField(
            value = uiState.password,
            onValueChange = viewModel::updateLoginPassword,
            label = "Password",
            modifier = Modifier.padding(bottom = 24.dp),
            isError = uiState.error != null,
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

        // Login Button
        AuthButton(
            text = "Sign In",
            onClick = viewModel::login,
            modifier = Modifier.padding(bottom = 16.dp),
            enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank(),
            isLoading = uiState.isLoading
        )

        // Register Link
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}