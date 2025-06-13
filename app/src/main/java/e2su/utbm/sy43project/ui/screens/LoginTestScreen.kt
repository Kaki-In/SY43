package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import e2su.utbm.sy43project.data.models.SelfUiState
import e2su.utbm.sy43project.data.models.SelfViewModel

@Composable
public fun LoginTestScreen(
    viewModel: SelfViewModel,
    modifier: Modifier = Modifier
) {
    var disconnecting by remember {
        mutableStateOf(false)
    }

    var refreshing by remember {
        mutableStateOf(false)
    }

    if (disconnecting)
    {
        LaunchedEffect(
            key1 = "oasidj"
        ) {
            viewModel.logout()

            disconnecting = false
        }
    }

    if (refreshing)
    {
        LaunchedEffect(
            key1 = "oasidj"
        ) {
            viewModel.updateConnection()

            refreshing = false
        }
    }

    Column {
        when (viewModel.selfState.value)
        {
            is SelfUiState.Unknown ->
            {
                LaunchedEffect(
                    key1 = true
                ) {
                    viewModel.updateConnection()
                }
            }

            is SelfUiState.Loading ->
            {
                Text("Chargement...")
            }

            is SelfUiState.Disconnected ->
            {
                LoginComposable(viewModel)
            }

            is SelfUiState.CantConnect ->
            {
                LoginComposable(viewModel)
            }


            is SelfUiState.Connecting ->
            {
                LoginComposable(viewModel)
            }

            is SelfUiState.Connected ->
            {
                val account = (viewModel.selfState.value as SelfUiState.Connected).account
                Text("Bonjour " + account.profile.firstName + " " + account.profile.lastName + "!")
                Text("Il vous reste " + account.safe!!.quota + " nooblards.")

                Button(
                    onClick = {
                        disconnecting = true
                    }
                ) {
                    Text("Se déconnecter")
                }

            }
        }

        Button(
            onClick = {
                refreshing = true
            }
        ) {
            Text("Rafraichir")
        }

    }


}

@Composable
fun LoginComposable(
    viewModel: SelfViewModel,
    modifier: Modifier = Modifier)
{
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var connecting by remember {
        mutableStateOf(false)
    }

    if (connecting)
        LaunchedEffect(key1 = true) {
            viewModel.login(username, password)

            connecting = false
        }

    Column (modifier = modifier) {
        TextField(
            username,
            onValueChange = {
                username = it
            },
            placeholder = {
                Text("Nom d'utilisateur")
            }
        )

        TextField(
            password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text("Mot de passe")
            }
        )

        Button(
            enabled = viewModel.selfState.value !is SelfUiState.Connecting,
            onClick = {
                connecting = true
            }
        ) {
            Text("Se connecter")
        }

        if (viewModel.selfState.value is SelfUiState.CantConnect)
        {
            Text("Impossible de se connecter: " + (viewModel.selfState.value as SelfUiState.CantConnect).message)
        }
    }
}

