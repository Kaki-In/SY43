package e2su.utbm.sy43project.ui.appsides

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.data.models.MainViewModel
import e2su.utbm.sy43project.navigation.NavRoutes
import e2su.utbm.sy43project.navigation.NavigationManager
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.screens.common.ActivityScreen
import e2su.utbm.sy43project.ui.screens.common.ClassScreen
import e2su.utbm.sy43project.ui.screens.common.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.common.OverviewScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
import e2su.utbm.sy43project.ui.screens.common.ShopScreen
import e2su.utbm.sy43project.ui.screens.student.StudentHomeScreen
import e2su.utbm.sy43project.ui.screens.teacher.TeacherHomeScreen


@Composable
fun ConnectedAsTeacherAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        NavigationManager.setProfileClickAction {
            navController.navigate(NavRoutes.PROFILE.route)
        }

    }

    NoobleIntegrated(
        navHostController = navController,
        drawerState = drawerState,
        scope = scope,
        modifier = modifier,
        content = {
            NavHost(navController, startDestination = NavRoutes.HOME.route) {
                composable(NavRoutes.HOME.route) {
                    TeacherHomeScreen(navController, viewModel)
                }
                composable(NavRoutes.PROFILE.route) {
                    ProfileScreen(
                        ProfileModel(1, "bonjour", "bonjour", 34, "salut", "aslaut", true, mutableListOf()),
                        onClassClick = { className ->
                            navController.navigate(NavRoutes.createClassRoute(className))
                        }
                    )
                }
                composable(NavRoutes.CLASS.route) {
                    ClassScreen(navController)
                }
                composable(NavRoutes.ACTIVITY.route) {
                    ActivityScreen()
                }
                composable(NavRoutes.CLASS_SELECT.route) {
                    ClassSelectScreen(navController = navController, courses = listOf())
                }
                composable(NavRoutes.CLASS_OVERVIEW.route) {
                    OverviewScreen()
                }
                composable(NavRoutes.SHOP.route) {
                    ShopScreen(
                        userCoins = 500,
                        shopItems = SampleData.shopItems,
                        onBuyItem = {}
                    )
                }
                composable(
                    route = NavRoutes.CLASS_DETAIL.route,
                    arguments = listOf(
                        navArgument("className") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    ClassScreen(navController)
                }
                composable(NavRoutes.PROFILE_EDIT.route) {
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
            }
        }
    )

}


