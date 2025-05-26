package e2su.utbm.sy43project.navigation

import android.util.Log

object NavigationManager {
    private var onProfileClick: (() -> Unit)? = null

    fun setProfileClickAction(action: () -> Unit) {
        onProfileClick = action
    }

    fun navigateToProfile() {
        onProfileClick?.invoke()
        Log.e("NavigationManager", "Navigating to profile")
    }
}