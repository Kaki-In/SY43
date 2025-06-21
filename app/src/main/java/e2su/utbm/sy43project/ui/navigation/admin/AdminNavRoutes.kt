package e2su.utbm.sy43project.ui.navigation.admin

enum class AdminNavRoutes(val route: String) {
    HOME("home"),
    PROFILE("profile/{accountName}"),
    ACTIVITY_THREAD("thread"),
    CLASS_OVERVIEW("class_overview/{className}"),
    CLASS_DETAIL("class/{className}"),
    SHOP("shop"),
    SETTINGS("settings"),
    DOWNLOADS("downloads"),
    ALL_CLASSES("all_classes");

    companion object {
        fun createClassOverviewRoute(className: String) = "class_overview/$className"
        fun createClassDetailsRoute(className: String) = "class/$className"
        fun createProfileRoute(accountName: String) = "profile/$accountName"
    }
}

