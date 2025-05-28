package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import e2su.utbm.sy43project.navigation.NavRoutes
import androidx.navigation.navArgument
import androidx.navigation.NavType
import e2su.utbm.sy43project.ui.screens.ProfileEditScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SY43ProjectTheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    NavigationManager.setProfileClickAction {
                        navController.navigate(NavRoutes.PROFILE.route)
                    }
                }
                NavHost(navController, startDestination = NavRoutes.LOGIN.route) {
                    composable(NavRoutes.LOGIN.route) {
                        LoginView(navController)
                    }
                    composable(NavRoutes.PROFILE.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            ProfileScreen(
                                SampleData.sampleProfile,
                                onClassClick = { className ->
                                    navController.navigate(NavRoutes.createClassRoute(className))
                                }
                            )
                        })
                    }
                    composable(NavRoutes.CLASS.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            ClassScreen(navController)
                        })
                    }
                    composable(NavRoutes.ACTIVITY.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            ActivityScreen()
                        })
                    }
                    composable(NavRoutes.CLASS_SELECT.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            ClassSelectScreen(navController = navController, courses = listOf())
                        })
                    }
                    composable(NavRoutes.CLASS_OVERVIEW.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            OverviewScreen()
                        })
                    }
                    composable(
                        route = NavRoutes.CLASS_DETAIL.route,
                        arguments = listOf(
                            navArgument("className") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val className = backStackEntry.arguments?.getString("className") ?: ""
                        NoobleIntegrated(navHostController = navController, content = {
                            ClassScreen(navController)
                        })
                    }
                    composable(NavRoutes.PROFILE_EDIT.route) {
                        NoobleIntegrated(navHostController = navController, content = {
                            ProfileEditScreen(
                                profile = SampleData.sampleProfile,
                                onSaveClick = { updatedProfile ->
                                    // TODO: Appel API pour sauvegarder les modifications
                                    },
                                onNavigateToProfile = {
                                   navController.navigate(NavRoutes.PROFILE.route) {
                                       popUpTo(NavRoutes.PROFILE.route) { inclusive = true}
                                   }
                                }
                            )
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