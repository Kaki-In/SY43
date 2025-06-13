package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.nooble.api.models.objects.NoobleApiAccountModel
import e2su.nooble.api.models.requests.LoginRequestModel
import e2su.nooble.api.models.responses.LoginResponseModel
import e2su.nooble.api.service.NoobleApi
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.navigation.NavRoutes
import e2su.utbm.sy43project.navigation.NavigationManager
import e2su.utbm.sy43project.ui.api.RequestViewModel
import e2su.utbm.sy43project.ui.components.NoobleDrawer
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.screens.ActivityScreen
import e2su.utbm.sy43project.ui.screens.ClassScreen
import e2su.utbm.sy43project.ui.screens.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.LoginTestScreen
import e2su.utbm.sy43project.ui.screens.LoginView
import e2su.utbm.sy43project.ui.screens.OverviewScreen
import e2su.utbm.sy43project.ui.screens.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.ProfileScreen
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accountViewModel = RequestViewModel<Any?, NoobleApiAccountModel?>()
        val loginViewModel = RequestViewModel<LoginRequestModel, LoginResponseModel>()
        val disconnectViewModel = RequestViewModel<Any?, Any?>()

        setContent {
            val noobleApi = NoobleApi(LocalContext.current, "https://api.nooble-angular.flopcreation.fr")
            SY43ProjectTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    NavigationManager.setProfileClickAction {
                        navController.navigate(NavRoutes.PROFILE.route)
                    }
                }

                // Utiliser le NoobleDrawer comme wrapper de toute l'application
                NoobleDrawer(
                    navController = navController,
                    drawerState = drawerState
                ) {
                    NavHost(navController, startDestination = NavRoutes.LOGIN.route) {
                        composable(NavRoutes.LOGIN.route) {
                            LoginView(navController)
                        }
                        composable(NavRoutes.CONNECTION_TEST.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content =
                                    {
                                        LoginTestScreen(
                                            accountViewModel,
                                            loginViewModel,
                                            disconnectViewModel,
                                            noobleApi
                                        )
                                    }
                            )
                        }
                        composable(NavRoutes.PROFILE.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ProfileScreen(
                                        SampleData.sampleProfile,
                                        onClassClick = { className ->
                                            navController.navigate(NavRoutes.createClassRoute(className))
                                        }
                                    )
                                }
                            )
                        }
                        composable(NavRoutes.CLASS.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassScreen(navController)
                                }
                            )
                        }
                        composable(NavRoutes.ACTIVITY.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ActivityScreen()
                                }
                            )
                        }
                        composable(NavRoutes.CLASS_SELECT.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassSelectScreen(navController = navController, courses = listOf())
                                }
                            )
                        }
                        composable(NavRoutes.CLASS_OVERVIEW.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    OverviewScreen()
                                }
                            )
                        }
                        /**composable(NavRoutes.SHOP.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ShopScreen()
                                }
                            )
                        }**/
                        composable(
                            route = NavRoutes.CLASS_DETAIL.route,
                            arguments = listOf(
                                navArgument("className") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val className = backStackEntry.arguments?.getString("className") ?: ""
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassScreen(navController)
                                }
                            )
                        }
                        composable(NavRoutes.PROFILE_EDIT.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ProfileEditScreen(
                                        profile = SampleData.sampleProfile,
                                        onSaveClick = { updatedProfile ->
                                            // TODO: Appel API pour sauvegarder les modifications
                                        },
                                        onNavigateToProfile = {
                                            navController.navigate(NavRoutes.PROFILE.route) {
                                                popUpTo(NavRoutes.PROFILE.route) { inclusive = true }
                                            }
                                        }
                                    )
                                }
                            )
                        }
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