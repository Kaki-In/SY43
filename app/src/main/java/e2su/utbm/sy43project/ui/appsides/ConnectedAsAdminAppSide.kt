package e2su.utbm.sy43project.ui.appsides

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.drawers.AdminDrawer
import e2su.utbm.sy43project.ui.drawers.StudentOrTeacherDrawer
import e2su.utbm.sy43project.ui.navgraphs.AdminNavGraph
import e2su.utbm.sy43project.ui.navgraphs.StudentOrTeacherNavGraph
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.SelfUiState
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ConnectedAsAdminAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val selfAccount = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    AdminDrawer (
        selfViewModel = viewModel.selfViewModel,
        drawerState = drawerState,
        modifier = modifier
    ) {
        NoobleIntegrated(
            onOpenHome = {
                AdminNavigationManager.homePageAction.navigate()
            },
            onOpenShop = {
                AdminNavigationManager.shopPageAction.navigate()
            },
            onOpenThread = {
                AdminNavigationManager.threadPageAction.navigate()
            },
            onOpenClasses = {
                AdminNavigationManager.allClassesPageAction.navigate()
            },
            viewModel = viewModel,
            onToggleDrawerState = {
                scope.launch {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
            },
            modifier = modifier,
            content = {
                AdminNavGraph(
                    viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            },
            onProfileClicked = {
                AdminNavigationManager.profilePageAction.navigate(selfAccount.id)
            }
        )
    }

}


