package e2su.utbm.sy43project.ui.navigation.admin

enum class AdminNavRoutes(val route: String) {
    LOGIN("login"),
    HOME("home"),
    PROFILE("profile"),
    ACTIVITY("activity"),
    CLASS_SELECT("select"),
    CLASS_LIST("class_list"),
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