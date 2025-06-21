package e2su.utbm.sy43project.ui.drawers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import e2su.utbm.sy43project.ui.navigation.disconnected.DisconnectedNavigationManager
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.viewmodels.SelfViewModel

data class AdminDrawerItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun AdminDrawer(
    selfViewModel: SelfViewModel,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val items: List<AdminDrawerItem> = listOf(
        AdminDrawerItem("Downloads", Icons.Default.Download) { AdminNavigationManager.downloadsPageAction.navigate() },
        AdminDrawerItem("Account settings", Icons.Default.Settings) { AdminNavigationManager.settingsPageAction.navigate() },
        AdminDrawerItem("Logout", Icons.Default.Logout) {
            scope.launch{
                selfViewModel.logout()
            }

        }
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Menu Nooble",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            item.onClick()
                        },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    ) {
        content()
    }
}