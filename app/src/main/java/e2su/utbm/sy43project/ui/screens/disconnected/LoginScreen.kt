package e2su.utbm.sy43project.ui.screens.disconnected

import android.util.Log
import e2su.utbm.sy43project.R;
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.viewmodels.SelfUiState
import e2su.utbm.sy43project.viewmodels.SelfViewModel
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.ui.navgraphs.DisconnectedNavGraph
import e2su.utbm.sy43project.ui.navigation.disconnected.DisconnectedNavigationManager
import e2su.utbm.sy43project.ui.theme.NoobleGreen

// TODO: Check how to round the corners of the the buttons now that I changed their background color

@Composable
fun LoginScreen(
    viewModel: SelfViewModel,
    modifier: Modifier = Modifier
) {
    val selfState = viewModel.selfState

    var firstEmail: String
    var firstPassword: String

    if (selfState.value is SelfUiState.Connecting)
    {
        val cantConnectSelfState = selfState.value as SelfUiState.Connecting
        firstEmail = cantConnectSelfState.username
        firstPassword = cantConnectSelfState.password
    } else if (selfState.value is SelfUiState.CantConnect)
    {
        val cantConnectSelfState = selfState.value as SelfUiState.CantConnect
        firstEmail = cantConnectSelfState.username
        firstPassword = cantConnectSelfState.password
    }
    else {
        firstEmail = ""
        firstPassword = ""
    }

    var email by remember { mutableStateOf(firstEmail) }
    var password by remember { mutableStateOf(firstPassword) }

    var errorMailMessage by remember { mutableStateOf("") }

    var connecting by remember { mutableStateOf(false) }

    if (connecting)
        LaunchedEffect(key1=true) {
            viewModel.login(email, password)
        }

        connecting = false

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Log In",
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
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                connecting = true
            },
            enabled = selfState.value !is SelfUiState.Connecting,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Black),
        ) {
            if (selfState.value is SelfUiState.Connecting)
            {
                LoadingSpinner()
            } else {
                Text(text = "Login")
            }
        }

        if (selfState.value is SelfUiState.CantConnect)
        {
            val selfCantConnectState = (selfState.value as SelfUiState.CantConnect)
            val errorMessage = selfCantConnectState.message

            if (errorMessage.startsWith("HTTP 401"))
            {
                Text("Nom d'utilisateur ou mot de passe incorrect")
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
                DisconnectedNavigationManager.forgotPasswordPageAction.navigate()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Forgot password ?",
                color = Color.Black,
            )
        }
    }
}

