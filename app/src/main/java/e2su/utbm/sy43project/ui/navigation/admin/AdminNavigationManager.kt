package e2su.utbm.sy43project.ui.navigation.admin

import e2su.tools.navigate.NavigateActionManager
import e2su.tools.navigate.NeedsIdNavigateActionManager

object AdminNavigationManager {
    val homePageAction = NavigateActionManager()
    val profilePageAction = NeedsIdNavigateActionManager<String>()
    val threadPageAction = NavigateActionManager()
    val classOverviewPageAction = NeedsIdNavigateActionManager<String>()
    val allClassesPageAction = NavigateActionManager()
    val usersPageAction = NavigateActionManager()
    val classDetailsPageAction = NeedsIdNavigateActionManager<String>()
    val shopPageAction = NavigateActionManager()
    val settingsPageAction = NavigateActionManager()
    val downloadsPageAction = NavigateActionManager()
    val addUserToClass = NeedsIdNavigateActionManager<String>()
    val editUserPageAccountAction = NeedsIdNavigateActionManager<String>()
    val createUserPageAction = NavigateActionManager()
}

