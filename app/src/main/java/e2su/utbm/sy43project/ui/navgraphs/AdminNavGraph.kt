package e2su.utbm.sy43project.ui.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavRoutes
import e2su.utbm.sy43project.ui.screens.admin.AdminHomeScreen
import e2su.utbm.sy43project.ui.screens.common.ActivityScreen
import e2su.utbm.sy43project.ui.screens.common.ClassScreen
import e2su.utbm.sy43project.ui.screens.common.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.common.OverviewScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
import e2su.utbm.sy43project.ui.screens.common.ShopScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel

@Composable
fun AdminNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val navController = rememberNavController()

    AdminNavigationManager.profilePageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.PROFILE.route)
    }

    NavHost(navController, startDestination = AdminNavRoutes.HOME.route, modifier = modifier) {
        composable(AdminNavRoutes.HOME.route) {
            AdminHomeScreen(navController, viewModel)
        }

        composable(AdminNavRoutes.PROFILE.route) {
            ProfileScreen(
                ProfileModel(1, "bonjour", "bonjour", 34, "salut", "aslaut", true, mutableListOf()),
                onClassClick = { className ->
                    navController.navigate(AdminNavRoutes.createClassRoute(className))
                }
            )
        }

        composable(AdminNavRoutes.CLASS.route) {
            ClassScreen(navController)
        }

        composable(AdminNavRoutes.ACTIVITY.route) {
            ActivityScreen()
        }

        composable(AdminNavRoutes.CLASS_SELECT.route) {
            ClassSelectScreen(navController = navController, courses = listOf())
        }

        composable(StudentOrTeacherNavRoutes.CLASS_OVERVIEW.route) {  entry ->
            val className = entry.arguments?.getString("className")!!
            val request = viewModel.createRetrieveDataViewModel<NoobleApiClassModel>()

            OverviewScreen(
                classId = className,
                requestViewModel = request
            )
        }

        composable(AdminNavRoutes.SHOP.route) {
            ShopScreen(
                userCoins = 500,
                shopItems = SampleData.shopItems,
                onBuyItem = {}
            )
        }

        composable(
            route = AdminNavRoutes.CLASS_DETAIL.route,
            arguments = listOf(
                navArgument("className") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: ""
            ClassScreen(navController)
        }

        composable(AdminNavRoutes.PROFILE_EDIT.route) {
            ProfileEditScreen(
                profile = SampleData.sampleProfile,
                onSaveClick = { updatedProfile ->
                    // TODO: Appel API pour sauvegarder les modifications
                },
                onNavigateToProfile = {
                    navController.navigate(AdminNavRoutes.PROFILE.route) {
                        popUpTo(AdminNavRoutes.PROFILE.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
