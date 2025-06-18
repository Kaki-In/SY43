package e2su.utbm.sy43project.ui.screens.disconnected

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.responses.ForgotPasswordResponseModel
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.ui.navigation.disconnected.DisconnectedNavigationManager
import e2su.utbm.sy43project.viewmodels.CurrentActionUiState
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel

@Composable
fun ForgotPasswordScreen(modifier: Modifier = Modifier, viewModel: RetrieveDataViewModel<ForgotPasswordResponseModel>)
{
    val requestState = viewModel.requestState

    var email by remember { mutableStateOf("") }

    var errorMailMessage by remember { mutableStateOf("") }

    var processLaunched by remember { mutableStateOf(false) }

    if (processLaunched)
        LaunchedEffect(key1=true) {
            viewModel.retrieveData {

                viewModel.getNoobleApi().connection.launchForgotPasswordProcess(email)
            }
        }

    processLaunched = false

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Forgot Password",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = errorMailMessage,
            fontWeight = FontWeight.Bold,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )

        TextField(
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, autoCorrectEnabled = false),
            onValueChange = {
                email = it
                errorMailMessage = if (it.contains("@") && it.contains(".")) "" else "Invalid email"
            },
            placeholder = { Text("example@hey.yo") },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (requestState.value is CurrentDataRequestUiState.Success)
        {
            val successState = requestState.value as CurrentDataRequestUiState.Success
            Text("Hello ${successState.responseData.firstName} ${successState.responseData.lastName}. A new password has just been sent to your mail address.")
        } else {
            Button(
                onClick = {
                    processLaunched = true
                },
                enabled = requestState.value !is CurrentDataRequestUiState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Black),
            ) {
                if (requestState.value is CurrentDataRequestUiState.Loading)
                {
                    LoadingSpinner()
                } else {
                    Text(text = "Login")
                }
            }

        }

        if (requestState.value is CurrentDataRequestUiState.Error)
        {
            val cantProcessState = (requestState.value as CurrentDataRequestUiState.Error)
            val errorMessage = cantProcessState.reason

            if (errorMessage.startsWith("HTTP 400"))
            {
                Text("Adresse mail invalide")
            }

            else if (errorMessage.startsWith("HTTP 500"))
            {
                Text("Erreur du serveur. Merci de contacter votre administrateur")
            }

            else
            {
                Text("Une erreur inconnue s'est produite")
            }
        }

        Button(
            onClick = {
                DisconnectedNavigationManager.connectPageAction.navigate()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Retour",
                color = Color.Black,
            )
        }
    }
}


