package e2su.utbm.sy43project.ui.navgraphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import e2su.utbm.sy43project.api.models.responses.ForgotPasswordResponseModel
import e2su.utbm.sy43project.ui.navigation.disconnected.DisconnectedNavRoutes
import e2su.utbm.sy43project.ui.navigation.disconnected.DisconnectedNavigationManager
import e2su.utbm.sy43project.ui.screens.disconnected.ForgotPasswordScreen
import e2su.utbm.sy43project.ui.screens.disconnected.LoginScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@Composable
fun DisconnectedNavGraph(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val navController = rememberNavController()

    var forgotPasswordViewModel: RetrieveDataViewModel<ForgotPasswordResponseModel>? = null
    if (viewModel.selfViewModel.selfState.value is SelfUiState.Disconnected)
    {
        forgotPasswordViewModel = (viewModel.selfViewModel.selfState.value as SelfUiState.Disconnected).launchForgotPasswordRequest
    } else if (viewModel.selfViewModel.selfState.value is SelfUiState.CantConnect) {
        forgotPasswordViewModel = (viewModel.selfViewModel.selfState.value as SelfUiState.CantConnect).launchForgotPasswordRequest
    }

    DisconnectedNavigationManager.connectPageAction.setClickedAction {
        navController.navigate(DisconnectedNavRoutes.CONNECT.route)
    }

    if (forgotPasswordViewModel != null)
    DisconnectedNavigationManager.forgotPasswordPageAction.setClickedAction {
        forgotPasswordViewModel.forget()
        navController.navigate(DisconnectedNavRoutes.FORGOT_PASSWORD.route)
    }

    DisconnectedNavigationManager.downloadsPageAction.setClickedAction {
        navController.navigate(DisconnectedNavRoutes.DOWNLOADS.route)
    }

    NavHost(navController, startDestination = DisconnectedNavRoutes.CONNECT.route, modifier = modifier) {
        composable(DisconnectedNavRoutes.CONNECT.route) {
            LoginScreen(
                viewModel.selfViewModel,
                modifier = Modifier
            )
        }

        composable(DisconnectedNavRoutes.FORGOT_PASSWORD.route) {
            if (forgotPasswordViewModel != null)
            ForgotPasswordScreen(
                viewModel = forgotPasswordViewModel
            )
        }

        /*composable(DisconnectedNavRoutes.DOWNLOADS.route) {
            DownloadsScreen(
                downloadedFiles = viewModel.downloadedFilesViewModel.downloadedFiles,
            )
        }*/
    }
}

