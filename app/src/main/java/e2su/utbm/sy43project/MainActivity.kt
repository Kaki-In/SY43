package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import e2su.nooble.models.ClassModel
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import e2su.utbm.sy43project.ui.screens.ActivityScreen
import e2su.utbm.sy43project.ui.screens.OverviewScreen
import e2su.utbm.sy43project.ui.screens.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.ClassScreen
import e2su.utbm.sy43project.ui.screens.LoginView
import e2su.utbm.sy43project.ui.screens.ProfileScreen
import e2su.utbm.sy43project.navigation.NavigationManager



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SY43ProjectTheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    NavigationManager.setProfileClickAction {
                        navController.navigate("profile")
                    }
                }
                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginView(navController) }
                    composable("class") {
                        NoobleIntegrated(navHostController = navController, content={
                            ClassScreen(navController)
                        })
                    }
                    composable("profile") {
                        NoobleIntegrated(navHostController = navController, content = {
                            ProfileScreen(
                                SampleData.sampleProfile,
                                onClassClick = { className ->
                                navController.navigate("class/$className")
                            })
                        })
                    }
                    composable("activity") {
                        NoobleIntegrated(navHostController = navController, content={
                            ActivityScreen()
                        })
                    }
                    composable("select") {
                        NoobleIntegrated(navHostController = navController, content={
                            ClassSelectScreen(navController = navController, courses = listOf())
                        })
                    }
                    composable("class_overview") {
                        NoobleIntegrated(navHostController = navController, content={
                            OverviewScreen()
                        })
                    }
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