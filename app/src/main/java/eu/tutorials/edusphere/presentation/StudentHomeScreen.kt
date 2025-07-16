package eu.tutorials.edusphere.presentation

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import eu.tutorials.edusphere.Screen
//import eu.tutorials.edusphere.data.util.TokenManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//@Composable
//fun StudentHomeScreen(navController: NavController) {
//    Column(Modifier.fillMaxSize().padding(16.dp)) {
//        Text("Welcome Student")
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            CoroutineScope(Dispatchers.IO).launch {
//                TokenManager.clearTokens()
//                withContext(Dispatchers.Main) {
//                    navController.navigate(Screen.LOGIN) {
//                        popUpTo(0)
//                    }
//                }
//            }
//        }) {
//            Text("Logout")
//        }
//    }
//}
