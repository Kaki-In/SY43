package e2su.utbm.sy43project.ui.appsides

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavRoutes
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.drawers.StudentOrTeacherDrawer
import e2su.utbm.sy43project.ui.navgraphs.StudentOrTeacherNavGraph
import e2su.utbm.sy43project.ui.screens.common.ActivityScreen
import e2su.utbm.sy43project.ui.screens.common.ClassScreen
import e2su.utbm.sy43project.ui.screens.common.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.common.OverviewScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.common.ProfileScreen
import e2su.utbm.sy43project.ui.screens.common.ShopScreen
import e2su.utbm.sy43project.ui.screens.studentorteacher.StudentOrTeacherHomeScreen
import kotlinx.coroutines.launch


@Composable
fun ConnectedAsStudentOrTeacherAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    StudentOrTeacherDrawer(
        drawerState = drawerState
    ) {
        NoobleIntegrated(
            onToggleDrawerState = {
                scope.launch {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }
            },
            modifier = modifier,
            content = {
                StudentOrTeacherNavGraph(viewModel)
            }
        )
    }

}


