package e2su.utbm.sy43project.ui.appsides

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.drawers.AdminDrawer
import e2su.utbm.sy43project.ui.drawers.StudentOrTeacherDrawer
import e2su.utbm.sy43project.ui.navgraphs.AdminNavGraph
import e2su.utbm.sy43project.ui.navgraphs.StudentOrTeacherNavGraph
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ConnectedAsAdminTeacherAppSide(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var adminMode by rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val selfAccount = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    if (
        adminMode
    )
    {
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
                    Column {
                        AdminSwitch(
                            adminMode,
                            {
                                adminMode = it
                            }
                        )

                        AdminNavGraph(
                            viewModel,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
                onProfileClicked = {
                    AdminNavigationManager.profilePageAction.navigate(selfAccount.id)
                }
            )
        }

    } else {
        StudentOrTeacherDrawer(
            selfViewModel = viewModel.selfViewModel,
            drawerState = drawerState,
            modifier = modifier
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
                    Column {
                        AdminSwitch(
                            adminMode,
                            {
                                adminMode = it
                            }
                        )

                        StudentOrTeacherNavGraph(
                            viewModel,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                },
                onProfileClicked = {
                    StudentOrTeacherNavigationManager.profilePageAction.navigate(selfAccount.id)
                }
            )
        }

    }
}

@Composable
fun AdminSwitch(
    enabled: Boolean,
    onChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)
{
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    )
    {
        Switch(
            enabled,
            onCheckedChange = onChange
        )

        Spacer(Modifier.width(10.dp))

        Text("Active admin mode")
    }
}

