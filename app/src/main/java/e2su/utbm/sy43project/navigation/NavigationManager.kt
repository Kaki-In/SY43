package e2su.utbm.sy43project.navigation

object NavigationManager {
    private var onProfileClick: (() -> Unit)? = null

    fun setProfileClickAction(action: () -> Unit) {
        onProfileClick = action
    }

    fun navigateToProfile() {
        onProfileClick?.invoke()
    }
}