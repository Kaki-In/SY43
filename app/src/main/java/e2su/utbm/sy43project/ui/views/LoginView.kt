package e2su.utbm.sy43project.ui.views

import e2su.utbm.sy43project.R;
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import e2su.utbm.sy43project.ui.components.NoobleFooter
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import e2su.utbm.sy43project.ui.components.NoobleHeader
import e2su.utbm.sy43project.ui.theme.NoobleGreen
import java.nio.file.WatchEvent

// TODO: Check how to round the corners of the the buttons now that I changed their background color

var ErrorMailMessage : String = ""

@Composable
fun LoginBlock() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row {
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = ErrorMailMessage,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
        TextField(
            value = email,
            onValueChange = {
                email = it
                ErrorMailMessage = if (it.contains("@") && it.contains(".")) "" else "Invalid email"
            },
            placeholder = { Text("example@hey.yo") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Password",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                // Vérifiez si l'email et le mot de passe sont valides
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Black),
        ) {
            Text(text = "Login")
        }
        Button(
            onClick = {
                // Action pour "Mot de passe oublié"
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

@Composable
fun NoobleLoginHeader() {
    Box (modifier = Modifier.background(NoobleGreen).fillMaxWidth()) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null
        )

        Text (
            "Nooble",
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFFFFFFFF),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LoginView(navController: NavHostController) {
    Scaffold { innerPadding ->
        Column() {
            Spacer(modifier = Modifier.height(innerPadding.calculateTopPadding()).background(NoobleGreen).fillMaxWidth())
            NoobleLoginHeader()
            Spacer(modifier = Modifier.size(30.dp))
            /*Text(
                text = "Il faut se login, $name",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )*/
            LoginBlock()
            Spacer(modifier = Modifier.size(70.dp))
            Button(onClick = { navController.navigate("activity") }) {
                Text(text = "Go to Profile Screen")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    SY43ProjectTheme {
        LoginView(NavHostController)
    }
}*/