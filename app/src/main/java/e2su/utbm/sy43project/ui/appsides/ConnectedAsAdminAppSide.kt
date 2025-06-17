package e2su.utbm.sy43project.ui.appsides

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import e2su.utbm.sy43project.ui.components.NoobleDrawer
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.drawers.AdminDrawer
import e2su.utbm.sy43project.ui.navgraphs.AdminNavGraph
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.SelfUiState
import kotlinx.coroutines.launch


@Composable
fun ConnectedAsAdminAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val selfAccount = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    AdminDrawer(
        drawerState = drawerState
    )
    {
        NoobleIntegrated(
            viewModel = viewModel,
            onToggleDrawerState = {
                scope.launch {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
            },
            modifier = modifier,
            content = {
                AdminNavGraph(
                    viewModel
                )
            },
            onProfileClicked = {
                StudentOrTeacherNavigationManager.profilePageAction.navigate(selfAccount.id)
            }
        )

    }

}


