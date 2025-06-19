package e2su.utbm.sy43project.ui.navgraphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.local.DownloadedFileEntity
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavRoutes
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.ui.screens.common.*
import e2su.utbm.sy43project.ui.screens.studentorteacher.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.studentorteacher.StudentOrTeacherHomeScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import java.io.File

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun StudentOrTeacherNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val connectedSelfState = viewModel.selfViewModel.selfState.value as SelfUiState.Connected

    StudentOrTeacherNavigationManager.classOverviewPageAction.setClickedAction { classId ->
        viewModel.overviewClassRequest.forget()
        navController.navigate(StudentOrTeacherNavRoutes.createClassOverviewRoute(classId))
    }

    StudentOrTeacherNavigationManager.downloadsPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.DOWNLOADS.route)
    }

    StudentOrTeacherNavigationManager.profilePageAction.setClickedAction { profileId ->
        viewModel.retrieveProfileRequest.forget()
        navController.navigate(StudentOrTeacherNavRoutes.createProfileRoute(profileId))
    }

    StudentOrTeacherNavigationManager.homePageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.HOME.route)
    }

    StudentOrTeacherNavigationManager.shopPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.SHOP.route)
    }

    StudentOrTeacherNavigationManager.classDetailsPageAction.setClickedAction { classId ->
        viewModel.retrieveClassesListRequest.forget()
        navController.navigate(StudentOrTeacherNavRoutes.createClassDetailsRoute(classId))
    }

    StudentOrTeacherNavigationManager.classSelectPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.CLASS_SELECT.route)
    }

    StudentOrTeacherNavigationManager.notificationDetailsPageAction.setClickedAction { notificationId ->
        navController.navigate(StudentOrTeacherNavRoutes.createNotificationDetailsRoute(notificationId))
    }

    StudentOrTeacherNavigationManager.profileEditPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.PROFILE_EDIT.route)
    }

    StudentOrTeacherNavigationManager.threadPageAction.setClickedAction {
        viewModel.retrieveThreadRequest.forget()
        navController.navigate(StudentOrTeacherNavRoutes.ACTIVITY_THREAD.route)
    }

    StudentOrTeacherNavigationManager.settingsPageAction.setClickedAction {
        navController.navigate(StudentOrTeacherNavRoutes.SETTINGS.route)
    }

    NavHost(navController, startDestination = StudentOrTeacherNavRoutes.HOME.route, modifier = modifier.fillMaxSize()) {
        composable(StudentOrTeacherNavRoutes.HOME.route) {
            StudentOrTeacherHomeScreen(viewModel)
        }

        composable(StudentOrTeacherNavRoutes.PROFILE.route) { entry ->
            val accountName = entry.arguments?.getString("accountName")!!

            ProfileScreen(
                viewModel,
                accountName,
                onClassClick = { className ->
                    if (connectedSelfState.account.profile.classes!!.contains(className))
                        navController.navigate(StudentOrTeacherNavRoutes.createClassOverviewRoute(className))
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
            ClassSelectScreen(
                viewModel,
                onClassClicked = {
                    StudentOrTeacherNavigationManager.classOverviewPageAction.navigate(it)
                },
                classesRequestViewModel = viewModel.retrieveClassesListRequest
            )
        }

        composable(StudentOrTeacherNavRoutes.CLASS_OVERVIEW.route) { entry ->
            val className = entry.arguments?.getString("className")!!

            OverviewScreen(
                classId = className,
                requestViewModel = viewModel.overviewClassRequest
            )
        }

        composable(StudentOrTeacherNavRoutes.SHOP.route) {
            ShopScreen(viewModel = viewModel)
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

        composable(StudentOrTeacherNavRoutes.DOWNLOADS.route) {
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
                        // ou setDataAndType(fileUri, file.mimeType ?: "application/octet-stream")
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

        composable(StudentOrTeacherNavRoutes.SETTINGS.route) {
            Text("Settings not available")
        }

        composable(StudentOrTeacherNavRoutes.ACTIVITY_THREAD.route) {
            ActivitiesThreadScreen(
                requestModel = viewModel.retrieveThreadRequest,
            )
        }
    }
}