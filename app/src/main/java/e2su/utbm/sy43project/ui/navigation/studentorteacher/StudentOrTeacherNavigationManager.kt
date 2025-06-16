package e2su.utbm.sy43project.ui.navigation.studentorteacher

import e2su.tools.navigate.NavigateActionManager
import e2su.tools.navigate.NeedsIdNavigateActionManager

object StudentOrTeacherNavigationManager{
    val homePageAction = NavigateActionManager()
    val profilePageAction = NeedsIdNavigateActionManager<String>()
    val threadPageAction = NavigateActionManager()
    val notificationDetailsPageAction = NeedsIdNavigateActionManager<String>()
    val classSelectPageAction = NavigateActionManager()
    val classOverviewPageAction = NeedsIdNavigateActionManager<String>()
    val classDetailsPageAction = NeedsIdNavigateActionManager<String>()
    val profileEditPageAction = NavigateActionManager()
    val shopPageAction = NavigateActionManager()
    val settingsPageAction = NavigateActionManager()
    val downloadsPageAction = NavigateActionManager()
}
