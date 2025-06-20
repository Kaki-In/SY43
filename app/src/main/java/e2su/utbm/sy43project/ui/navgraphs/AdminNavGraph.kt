package e2su.utbm.sy43project.ui.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavRoutes
import e2su.utbm.sy43project.ui.screens.admin.AdminAllClassesScreen
import e2su.utbm.sy43project.ui.screens.admin.AdminHomeScreen
import e2su.utbm.sy43project.ui.screens.common.ActivityScreen
import e2su.utbm.sy43project.ui.screens.common.ClassDetailsScreen
import e2su.utbm.sy43project.ui.screens.common.ClassOverviewScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
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

        composable(StudentOrTeacherNavRoutes.PROFILE.route) { entry ->
            val accountName = entry.arguments?.getString("accountName")!!

            ProfileScreen(
                viewModel,
                accountName,
                onClassClick = { className ->
                    navController.navigate(
                        StudentOrTeacherNavRoutes.createClassDetailsRoute(
                            className
                        )
                    )
                }
            )
        }

        composable(AdminNavRoutes.ACTIVITY.route) {
            ActivityScreen()
        }

        composable(AdminNavRoutes.CLASS_SELECT.route) {
//            ClassSelectScreen(viewModel, classesRequestViewModel)
        }

        composable(StudentOrTeacherNavRoutes.CLASS_OVERVIEW.route) {  entry ->
            val className = entry.arguments?.getString("className")!!

            ClassOverviewScreen(
                classId = className,
                requestViewModel = viewModel.overviewClassRequest
            )
        }

        composable(AdminNavRoutes.SHOP.route) {
        }

        composable(AdminNavRoutes.CLASS_LIST.route){
            AdminAllClassesScreen(
                classesRequestViewModel = viewModel.retrieveClassesListRequest,
                onClassClicked = { className ->
                    navController.navigate(
                        AdminNavRoutes.CLASS_DETAIL.route + "/$className"
                    )
                },
            )
        }

        composable(
            route = AdminNavRoutes.CLASS_DETAIL.route,
            arguments = listOf(
                navArgument("className") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("className") ?: ""
            ClassDetailsScreen(
                viewModel,
                classId
            )
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
