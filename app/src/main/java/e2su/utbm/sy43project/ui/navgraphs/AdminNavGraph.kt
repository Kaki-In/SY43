package e2su.utbm.sy43project.ui.navgraphs

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.local.DownloadedFileEntity
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.ui.screens.admin.AdminAddUserToClassScreen
import e2su.utbm.sy43project.ui.screens.admin.AdminAllClassesScreen
import e2su.utbm.sy43project.ui.screens.admin.AdminHomeScreen
import e2su.utbm.sy43project.ui.screens.admin.AdminSettingsScreen
import e2su.utbm.sy43project.ui.screens.admin.ModifyUserAccountScreen
import e2su.utbm.sy43project.ui.screens.admin.SelectManagingUserScreen
import e2su.utbm.sy43project.ui.screens.common.ActivitiesThreadScreen
import e2su.utbm.sy43project.ui.screens.common.ClassDetailsScreen
import e2su.utbm.sy43project.ui.screens.common.ClassOverviewScreen
import e2su.utbm.sy43project.ui.screens.common.DownloadsScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
import e2su.utbm.sy43project.ui.screens.common.ShopScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import java.io.File
import e2su.utbm.sy43project.ui.screens.admin.AdminCreateAccountScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AdminNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val connectedSelfState = viewModel.selfViewModel.selfState.value as SelfUiState.Connected

    AdminNavigationManager.classOverviewPageAction.setClickedAction { classId ->
        viewModel.overviewClassRequest.forget()
        navController.navigate(AdminNavRoutes.createClassOverviewRoute(classId))
    }

    AdminNavigationManager.allClassesPageAction.setClickedAction {
        viewModel.retrieveAllClassesRequest.forget()
        navController.navigate(AdminNavRoutes.ALL_CLASSES.route)
    }

    AdminNavigationManager.downloadsPageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.DOWNLOADS.route)
    }

    AdminNavigationManager.createUserPageAction.setClickedAction {
        viewModel.retrieveProfileRequest.forget()
        navController.navigate(AdminNavRoutes.CREATE_USER.route)
    }

    AdminNavigationManager.profilePageAction.setClickedAction { profileId ->
        viewModel.retrieveProfileRequest.forget()
        navController.navigate(AdminNavRoutes.createProfileRoute(profileId))
    }

    AdminNavigationManager.homePageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.HOME.route)
    }

    AdminNavigationManager.shopPageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.SHOP.route)
    }

    AdminNavigationManager.classDetailsPageAction.setClickedAction { classId ->
        viewModel.retrieveClassesListRequest.forget()
        navController.navigate(AdminNavRoutes.createClassDetailsRoute(classId))
    }

    AdminNavigationManager.threadPageAction.setClickedAction {
        viewModel.retrieveThreadRequest.forget()
        navController.navigate(AdminNavRoutes.ACTIVITY_THREAD.route)
    }

    AdminNavigationManager.settingsPageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.SETTINGS.route)
    }

    AdminNavigationManager.usersPageAction.setClickedAction {
        navController.navigate(AdminNavRoutes.ALL_USERS.route)
    }

    AdminNavigationManager.addUserToClass.setClickedAction { classId: String ->
        viewModel.retrieveClassesListRequest.forget()
        navController.navigate(AdminNavRoutes.createAddUserToClassRoute(classId))
    }

    AdminNavigationManager.editUserPageAccountAction.setClickedAction {
        viewModel.retrieveAccountRequest.forget()
        navController.navigate(AdminNavRoutes.createEditUserRoute(it))
    }

    NavHost(navController, startDestination = AdminNavRoutes.HOME.route, modifier = modifier.fillMaxSize()) {
        composable(AdminNavRoutes.HOME.route) {
            AdminHomeScreen(viewModel)
        }

        composable(AdminNavRoutes.PROFILE.route) { entry ->
            val accountName = entry.arguments?.getString("accountName")!!

            ProfileScreen(
                viewModel,
                accountName,
                onClassClick = { className ->
                    AdminNavigationManager.classOverviewPageAction.navigate(className)
                }
            )
        }

        composable(AdminNavRoutes.CLASS_OVERVIEW.route) { entry ->
            val className = entry.arguments?.getString("className")!!

            ClassOverviewScreen(
                viewModel,
                classId = className,
                requestViewModel = viewModel.overviewClassRequest,
                onOpenDetails = {
                    AdminNavigationManager.classDetailsPageAction.navigate(className)
                }
            )
        }

        composable(AdminNavRoutes.ALL_CLASSES.route) {
            AdminAllClassesScreen(
                classesRequestViewModel = viewModel.retrieveAllClassesRequest,
                onClassClicked = {
                    AdminNavigationManager.classOverviewPageAction.navigate(it)
                }
            )
        }

        composable(AdminNavRoutes.SHOP.route) {
            ShopScreen(viewModel = viewModel)
        }

        composable(AdminNavRoutes.CREATE_USER.route) {
            AdminCreateAccountScreen(
                viewModel,
                onBack = { AdminNavigationManager.profilePageAction.setClickedAction { profileId: String ->
                    viewModel.retrieveProfileRequest.forget()
                    navController.navigate(AdminNavRoutes.createProfileRoute(profileId))
                } }
            )
        }

        composable(
            route = AdminNavRoutes.CLASS_DETAIL.route
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("className") ?: ""

            ClassDetailsScreen(
                viewModel,
                classId,
                onAccountClicked = {
                    AdminNavigationManager.profilePageAction.navigate(it)
                },
                onAddUserClicked = {
                    AdminNavigationManager.addUserToClass.navigate(classId)
                }
            )
        }

        composable(AdminNavRoutes.DOWNLOADS.route) {
            val downloads = viewModel.downloadViewModel.downloads.collectAsState().value
            viewModel.downloadViewModel.getAllDownloads()

            val context = LocalContext.current
            val openFile: (DownloadedFileEntity) -> Unit = { file ->
                try {
                    val fileUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        File(file.filePath)
                    )

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(fileUri, "application/octet-stream")
                        // setDataAndType(fileUri, file.mimeType ?: "application/octet-stream")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Impossible d'ouvrir le fichier", Toast.LENGTH_SHORT).show()
                }
            }

            DownloadsScreen(
                downloadedFiles = downloads,
                onFileClick = openFile
            )
        }

        composable(AdminNavRoutes.SETTINGS.route) {
            AdminSettingsScreen(viewModel)
        }

        composable(AdminNavRoutes.ACTIVITY_THREAD.route) {
            ActivitiesThreadScreen(
                requestModel = viewModel.retrieveThreadRequest,
            )
        }

        composable(AdminNavRoutes.ALL_USERS.route) {
            SelectManagingUserScreen(
                viewModel,
                onAccountClicked = {
                    AdminNavigationManager.editUserPageAccountAction.navigate(it)
                },
                onCreateAccountClicked = {
                    AdminNavigationManager.createUserPageAction.navigate()
                }
            )
        }

        composable(AdminNavRoutes.ADD_USER_TO_CLASS.route) { backStackEntry ->
            val classId = backStackEntry.arguments?.getString("className") ?: ""

            AdminAddUserToClassScreen(
                mainViewModel = viewModel,
                classId = classId,
                onBack = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(AdminNavRoutes.EDIT_USER_ACCOUNT.route) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getString("accountId") ?: ""

            ModifyUserAccountScreen(
                viewModel,
                accountId = accountId
            )
        }

    }
}
