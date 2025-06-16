package e2su.utbm.sy43project.ui.appsides

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.navgraphs.AdminNavGraph


@Composable
fun ConnectedAsAdminAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NoobleIntegrated(
        drawerState = drawerState,
        scope = scope,
        modifier = modifier,
        content = {
            AdminNavGraph(
                viewModel
            )
        }
    )

}


