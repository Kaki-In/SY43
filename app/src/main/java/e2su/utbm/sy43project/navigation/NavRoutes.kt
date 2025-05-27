package e2su.utbm.sy43project.navigation

enum class NavRoutes(val route: String) {
    LOGIN("login"),
    CLASS("class"),
    PROFILE("profile"),
    ACTIVITY("activity"),
    CLASS_SELECT("select"),
    CLASS_OVERVIEW("class_overview"),
    CLASS_DETAIL("class/{className}");

    companion object {
        fun createClassRoute(className: String) = "class/$className"
    }
}