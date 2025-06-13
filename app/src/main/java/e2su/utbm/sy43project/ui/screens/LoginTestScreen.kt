package e2su.utbm.sy43project.ui.screens

import android.util.Log
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
import e2su.nooble.api.models.objects.NoobleApiAccountModel
import e2su.nooble.api.models.requests.LoginRequestModel
import e2su.nooble.api.models.responses.LoginResponseModel
import e2su.nooble.api.service.NoobleApi
import e2su.utbm.sy43project.ui.api.RequestUiState
import e2su.utbm.sy43project.ui.api.RequestViewModel

@Composable
public fun LoginTestScreen(
    connectionViewModel: RequestViewModel<Any?, NoobleApiAccountModel?>,
    loginViewModel: RequestViewModel<LoginRequestModel, LoginResponseModel>,
    disconnectViewModel: RequestViewModel<Any?, Any?>,
    noobleApi: NoobleApi,
    modifier: Modifier = Modifier
) {
    val connectionRequestState = connectionViewModel.requestState.value
    val loginRequestState = loginViewModel.requestState.value
    val disconnectRequestState = disconnectViewModel.requestState.value

    var disconnecting by remember {
        mutableStateOf(false)
    }

    if (disconnecting)
    {
        LaunchedEffect(key1=true) {
            disconnectViewModel.fetchRequest(null) {
                noobleApi.connection.logout()
            }
        }
        disconnecting = false
    }

    if (disconnectRequestState is RequestUiState.Loading)
    {
        connectionViewModel.invalidate()
        return
    } else if (disconnectRequestState is RequestUiState.Success || disconnectRequestState is RequestUiState.Error) {
        disconnectViewModel.invalidate()
    }

    if (connectionRequestState is RequestUiState.Idle)
    {
        if (loginRequestState is RequestUiState.Idle)
            LaunchedEffect(key1=true) {
                connectionViewModel.fetchRequest(
                    null
                ) { lrm ->
                    noobleApi.connection.getInformation()
                }
            }
        else
            loginViewModel.invalidate()
    }

    Column {
        Button(
            onClick = {
                connectionViewModel.invalidate()
            }
        ) {
            Text("Actualiser")
        }

        when (connectionRequestState)
        {
            is RequestUiState.Idle -> {
                Text("Bzzz bzz bzzz")
            }

            is RequestUiState.Loading -> {
                Text("Loading...")
            }

            is RequestUiState.Success -> {
                val account = connectionRequestState.response

                if (account == null)
                {
                    LoginComposable(connectionViewModel, loginViewModel, noobleApi)
                } else {
                    Text("Bonjour " + account.profile.firstName + " " + account.profile.lastName + "! Il vous reste " + account.safe!!.quota + " nooblards.")
                    Button(
                        onClick = {
                            disconnecting = true
                        }
                    ) {
                        Text("Se déconnecter")
                    }
                }
            }

            is RequestUiState.Error -> {
                Text("An error occurred :" + connectionRequestState.error)
                Button(
                    onClick = {
                        disconnecting = true
                    }
                ) {
                    Text("Se déconnecter")
                }
            }
        }

    }

}

@Composable
fun LoginComposable(
    connectionViewModel: RequestViewModel<Any?, NoobleApiAccountModel?>,
    loginViewModel: RequestViewModel<LoginRequestModel, LoginResponseModel>,
    noobleApi: NoobleApi,
    modifier: Modifier = Modifier)
{
    val connectionRequestState = connectionViewModel.requestState.value
    val loginRequestState = loginViewModel.requestState.value

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var connecting by remember {
        mutableStateOf(false)
    }

    if (loginRequestState is RequestUiState.Success)
    {
        connectionViewModel.invalidate()
        return
    }

    if (connecting)
        LaunchedEffect(key1 = true) {
            loginViewModel.fetchRequest(
                LoginRequestModel(
                    username,
                    password
                )
            ) { it ->
                val result = noobleApi.connection.login(it.username, it.password)
                return@fetchRequest result
            }

            connecting = false
        }

    Column {
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
            enabled = loginRequestState !is RequestUiState.Loading,
            onClick = {
                connecting = true
            }
        ) {
            Text("Se connecter")
        }
    }
}

