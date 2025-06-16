package e2su.utbm.sy43project.ui.navgraphs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavRoutes
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.ui.screens.common.ActivityScreen
import e2su.utbm.sy43project.ui.screens.common.ClassScreen
import e2su.utbm.sy43project.ui.screens.common.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.common.OverviewScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
import e2su.utbm.sy43project.ui.screens.common.ShopScreen
import e2su.utbm.sy43project.ui.screens.studentorteacher.StudentOrTeacherHomeScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel

@Composable
fun StudentOrTeacherNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val navController = rememberNavController()

    StudentOrTeacherNavigationManager.classOverviewPageAction.setClickedAction { classId ->
        Log.i("TAG", "Launching classSelectPage")
        navController.navigate(StudentOrTeacherNavRoutes.createClassOverviewRoute(classId))
    }

    StudentOrTeacherNavigationManager.downloadsPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.DOWNLOADS.route)
    }

    StudentOrTeacherNavigationManager.profilePageAction .setClickedAction { profileId ->
        navController.navigate(StudentOrTeacherNavRoutes.createProfileRoute(profileId))
    }

    StudentOrTeacherNavigationManager.homePageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.HOME.route)
    }

    StudentOrTeacherNavigationManager.shopPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.SHOP.route)
    }

    StudentOrTeacherNavigationManager.classDetailsPageAction.setClickedAction { classId ->
        navController.navigate(StudentOrTeacherNavRoutes.createClassDetailsRoute(classId))
    }

    StudentOrTeacherNavigationManager.classSelectPageAction .setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.CLASS_SELECT.route)
    }

    StudentOrTeacherNavigationManager.notificationDetailsPageAction .setClickedAction { notificationId ->
        navController.navigate(StudentOrTeacherNavRoutes.createNotificationDetailsRoute(notificationId))
    }

    StudentOrTeacherNavigationManager.profileEditPageAction .setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.PROFILE_EDIT.route)
    }

    StudentOrTeacherNavigationManager.threadPageAction .setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.ACTIVITY_THREAD.route)
    }

    StudentOrTeacherNavigationManager.settingsPageAction .setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.SETTINGS.route)
    }


    NavHost(navController, startDestination = StudentOrTeacherNavRoutes.HOME.route, modifier = modifier) {
        composable(StudentOrTeacherNavRoutes.HOME.route) {
            StudentOrTeacherHomeScreen(viewModel)
        }

        composable(StudentOrTeacherNavRoutes.PROFILE.route) {
            ProfileScreen(
                ProfileModel(1, "bonjour", "bonjour", 34, "salut", "aslaut", true, mutableListOf()),
                onClassClick = { className ->
                    navController.navigate(StudentOrTeacherNavRoutes.createClassDetailsRoute(className))
                }
            )
        }

        composable(StudentOrTeacherNavRoutes.CLASS.route) {
            ClassScreen(navController)
        }

        composable(StudentOrTeacherNavRoutes.NOTIFICATION_DETAILS.route) {
            ActivityScreen()
        }

        composable(StudentOrTeacherNavRoutes.CLASS_SELECT.route) {
            ClassSelectScreen(navController = navController, courses = listOf())
        }

        composable(StudentOrTeacherNavRoutes.CLASS_OVERVIEW.route) {
            OverviewScreen()
        }

        composable(StudentOrTeacherNavRoutes.SHOP.route) {
            ShopScreen(
                userCoins = 500,
                shopItems = SampleData.shopItems,
                onBuyItem = {}
            )
        }

        composable(
            route = StudentOrTeacherNavRoutes.CLASS_DETAIL.route,
            arguments = listOf(
                navArgument("className") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: ""
            ClassScreen(navController)
        }

        composable(StudentOrTeacherNavRoutes.PROFILE_EDIT.route) {
            ProfileEditScreen(
                profile = SampleData.sampleProfile,
                onSaveClick = { updatedProfile ->
                    // TODO: Appel API pour sauvegarder les modifications
                },
                onNavigateToProfile = {
                    navController.navigate(StudentOrTeacherNavRoutes.PROFILE.route) {
                        popUpTo(StudentOrTeacherNavRoutes.PROFILE.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
