package e2su.utbm.sy43project.ui.navigation.admin

import android.util.Log

object AdminNavigationManager {
    private var onProfileClick: (() -> Unit)? = null

    fun setProfileClickAction(action: () -> Unit) {
        onProfileClick = action
    }

    fun navigateToProfile() {
        onProfileClick?.invoke()
        Log.e("NavigationManager", "Navigating to profile")
    }
}

