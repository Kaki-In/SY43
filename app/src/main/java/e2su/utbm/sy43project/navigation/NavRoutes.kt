package e2su.utbm.sy43project.navigation

enum class NavRoutes(val route: String) {
    LOGIN("login"),
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
    DOWNLOAD("download");

    companion object {
        fun createClassRoute(className: String) = "class/$className"
    }
}