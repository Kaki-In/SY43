package e2su.utbm.sy43project.ui.appsides

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.drawers.StudentOrTeacherDrawer
import e2su.utbm.sy43project.ui.navgraphs.StudentOrTeacherNavGraph
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.SelfUiState
import kotlinx.coroutines.launch


@Composable
fun ConnectedAsStudentOrTeacherAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val selfAccount = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    StudentOrTeacherDrawer(
        selfViewModel = viewModel.selfViewModel,
        drawerState = drawerState
    ) {
        NoobleIntegrated(
            onOpenHome = {
                StudentOrTeacherNavigationManager.homePageAction.navigate()
            },
            onOpenShop = {
                StudentOrTeacherNavigationManager.shopPageAction.navigate()
            },
            onOpenThread = {
                StudentOrTeacherNavigationManager.threadPageAction.navigate()
            },
            onOpenClasses = {
                StudentOrTeacherNavigationManager.classSelectPageAction.navigate()
            },
            viewModel = viewModel,
            onToggleDrawerState = {
                scope.launch {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
            },
            modifier = modifier,
            content = {
                StudentOrTeacherNavGraph(viewModel)
            },
            onProfileClicked = {
                StudentOrTeacherNavigationManager.profilePageAction.navigate(selfAccount.id)
            }
        )
    }

}


