package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import e2su.utbm.sy43project.data.SampleData
import e2su.utbm.sy43project.navigation.NavRoutes
import e2su.utbm.sy43project.navigation.NavigationManager
import e2su.utbm.sy43project.ui.components.NoobleDrawer
import e2su.utbm.sy43project.ui.components.NoobleIntegrated
import e2su.utbm.sy43project.ui.screens.ActivityScreen
import e2su.utbm.sy43project.ui.screens.ClassScreen
import e2su.utbm.sy43project.ui.screens.ClassSelectScreen
import e2su.utbm.sy43project.ui.screens.LoginView
import e2su.utbm.sy43project.ui.screens.OverviewScreen
import e2su.utbm.sy43project.ui.screens.ProfileEditScreen
import e2su.utbm.sy43project.ui.screens.ProfileScreen
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import e2su.utbm.sy43project.ui.screens.ShopScreen
import e2su.utbm.sy43project.ui.screens.BorderPreviewScreen
import e2su.nooble.models.ProfileWithBadgesAndBorders
import e2su.nooble.models.ShopItemType
import e2su.nooble.models.BadgeShopItem
import e2su.nooble.models.BorderShopItem
import e2su.utbm.sy43project.data.SampleData.getBorderData
import e2su.utbm.sy43project.ui.screens.DownloadScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import e2su.utbm.sy43project.viewmodels.DownloadViewModel
import androidx.compose.runtime.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SY43ProjectTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    NavigationManager.setProfileClickAction {
                        navController.navigate(NavRoutes.PROFILE.route)
                    }
                }

                NoobleDrawer(
                    navController = navController,
                    drawerState = drawerState
                ) {
                    NavHost(navController, startDestination = NavRoutes.LOGIN.route) {
                        composable(NavRoutes.LOGIN.route) {
                            LoginView(navController)
                        }
                        composable(NavRoutes.PROFILE.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ProfileScreen(
                                        ProfileWithBadgesAndBorders(
                                            profile = SampleData.sampleProfile,
                                            badges = SampleData.shopItems.filterIsInstance<BadgeShopItem>(),
                                            borders = SampleData.shopItems.filterIsInstance<BorderShopItem>()
                                        ),
                                        onClassClick = { className ->
                                            navController.navigate(NavRoutes.createClassRoute(className))
                                        }
                                    )
                                }
                            )
                        }
                        composable(NavRoutes.CLASS.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassScreen(navController)
                                }
                            )
                        }
                        composable(NavRoutes.ACTIVITY.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    //ActivityScreen()
                                }
                            )
                        }
                        composable(NavRoutes.CLASS_SELECT.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassSelectScreen(navController = navController, courses = listOf())
                                }
                            )
                        }
                        composable(NavRoutes.CLASS_OVERVIEW.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    OverviewScreen()
                                }
                            )
                        }
                        composable(NavRoutes.SHOP.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ShopScreen(
                                        userCoins = 500,
                                        shopItems = SampleData.shopItems,
                                        onBuyItem = {}
                                    )
                                }
                            )
                        }
                        composable(NavRoutes.PREV_BORDER.route) {
                            val shopItems = remember { SampleData.shopItems.filter { it.itemType == ShopItemType.PROFILE_BORDER } }

                            BorderPreviewScreen(
                                availableBorders = shopItems.mapNotNull { item ->
                                    item.getBorderData()?.let { borderData ->
                                        item to borderData
                                    }
                                }
                            )
                        }
                        composable(
                            route = NavRoutes.CLASS_DETAIL.route,
                            arguments = listOf(
                                navArgument("className") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val className = backStackEntry.arguments?.getString("className") ?: ""
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ClassScreen(navController)
                                }
                            )
                        }
                        composable(NavRoutes.PROFILE_EDIT.route) {
                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    ProfileEditScreen(
                                        profile = SampleData.sampleProfile,
                                        onSaveClick = { updatedProfile ->
                                            // TODO: Appel API pour sauvegarder les modifications
                                        },
                                        onNavigateToProfile = {
                                            navController.navigate(NavRoutes.PROFILE.route) {
                                                popUpTo(NavRoutes.PROFILE.route) { inclusive = true }
                                            }
                                        },
                                        onChangePhoto = {
                                            // TODO: Implémenter la logique pour changer la photo
                                        },
                                        onChangeContour = {
                                            // TODO: Implémenter la logique pour changer le contour
                                        }
                                    )
                                }
                            )
                        }
                        composable(NavRoutes.DOWNLOAD.route) {
                            val downloadViewModel: DownloadViewModel = viewModel()
                            val downloadedFiles = downloadViewModel.downloadedFiles.collectAsState(initial = emptyList()).value

                            NoobleIntegrated(
                                navHostController = navController,
                                drawerState = drawerState,
                                scope = scope,
                                content = {
                                    DownloadScreen(downloadedFiles = downloadedFiles)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}