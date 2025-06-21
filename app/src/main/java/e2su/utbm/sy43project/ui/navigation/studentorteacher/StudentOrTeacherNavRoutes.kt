package e2su.utbm.sy43project.ui.navigation.studentorteacher

import e2su.tools.navigate.NavigateActionManager

enum class StudentOrTeacherNavRoutes(val route: String) {
    HOME("home"),
    PROFILE("profile/{accountName}"),
    ACTIVITY_THREAD("thread"),
    CLASS_SELECT("select"),
    CLASS_OVERVIEW("class_overview/{className}"),
    CLASS_DETAIL("class/{className}"),
    SHOP("shop"),
    SETTINGS("settings"),
    DOWNLOADS("downloads");

    companion object {
        fun createClassOverviewRoute(className: String) = "class_overview/$className"
        fun createClassDetailsRoute(className: String) = "class/$className"
        fun createProfileRoute(accountName: String) = "profile/$accountName"
    }
}
