package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import e2su.utbm.sy43project.ui.views.ActivityView
import e2su.utbm.sy43project.ui.views.ClassView
import e2su.utbm.sy43project.ui.views.LoginView
import e2su.utbm.sy43project.ui.views.ProfileView

/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SY43ProjectTheme {
                var isConnected by remember {
                    mutableStateOf(false)
                }

                if (isConnected) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Column(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            HorizontalPager(
                                state = rememberPagerState { 2 }
                            ) {
                            }
                        }
                    }
                }
            }
        }
    }
}*/


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SY43ProjectTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginView(navController) }
                    composable("class") { ClassView(navController) }
                    composable("profile") { ProfileView(navController) }
                    composable("activity") { ActivityView(navController) }
                }
            }
        }
    }
}
@Composable
fun MainPage() {
    val context = LocalContext.current
    Text("Bienvenue sur Nooble !")
    Spacer(modifier = Modifier.size(16.dp))
    Button(onClick = {

    }) { Text(
        text = "Page de login"
    ) }
    Spacer(modifier = Modifier.size(16.dp))
    Button(onClick = {

    }) { Text(
        text = "Page de profil"
    ) }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SY43ProjectTheme {
        MainPage()
    }
}