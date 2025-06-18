package e2su.utbm.sy43project.ui.navigation.shop

import e2su.tools.navigate.NavigateActionManager
import e2su.tools.navigate.NeedsIdNavigateActionManager

object ShopNavigationManager {
    val badgesPageAction = NavigateActionManager()
    val decorationsPageAction = NavigateActionManager()

    val buyBadgePageAction = NeedsIdNavigateActionManager<String>()
    val buyDecorationPageAction = NeedsIdNavigateActionManager<String>()
}