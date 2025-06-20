package e2su.utbm.sy43project.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@Composable
fun AdminHomeScreen(navController: NavHostController, viewModel: MainViewModel, modifier: Modifier = Modifier)
{
    val account = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    var logout by remember {
        mutableStateOf(false)
    }

    if (logout)
    {
        LaunchedEffect(true) {
            viewModel.selfViewModel.logout()
        }

        logout = false
    }

    Column(modifier) {
        Text("Bonjour, " + account.profile.firstName + " " + account.profile.lastName + "! Vous êtes connecté en tant qu'administrateur")

        Button(
            onClick = {
                logout = true
            }
        ) {
            Text("Se déconnecter")
        }

        Button(onClick = { navController.navigate("class_list") }) {
            Text(text = "Go to class list Screen")
        }
        Button(onClick = { navController.navigate("class") }) {
            Text(text = "Go to class Screen")
        }
        Button(onClick = { navController.navigate("select") }) {
            Text(text = "Go to class selection Screen")
        }
        Button(onClick = { navController.navigate("profile_edit") }) {
            Text(text = "Go to profile edit Screen")
        }
        Button (onClick = { navController.navigate("prev_border") }) {
            Text(text = "Border prev")
        }
    }
}



