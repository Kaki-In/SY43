package e2su.utbm.sy43project.ui.navigation.studentorteacher

import e2su.tools.navigate.NavigateActionManager

enum class NavRoutes(val route: String) {
    LOGIN("login"),
    HOME("home"),
    CLASS("class"),
    PROFILE("profile"),
    ACTIVITY("activity"),
    CLASS_SELECT("select"),
    CLASS_OVERVIEW("class_overview"),
    CLASS_DETAIL("class/{className}"),
    PROFILE_EDIT("profile_edit"),
    SHOP("shop"),
    GRADE("grade"),
    SETTINGS("settings"),
    DOWNLOADS("downloads");

    companion object {
        fun createClassRoute(className: String) = "class/$className"
        fun createProfileRoute(accountName: String) = "profile/$accountName"
    }
}

object DisconnectedNavigationManager {
    var connectPageAction = NavigateActionManager()
    var forgotPasswordPageAction = NavigateActionManager()
    var downloadsPageAction = NavigateActionManager()
}