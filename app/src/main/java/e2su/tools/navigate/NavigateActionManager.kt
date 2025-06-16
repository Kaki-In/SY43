package e2su.tools.navigate

import android.util.Log

class NavigateActionManager {
    private var clickedAction: (() -> Unit)? = null

    fun setClickedAction(action: () -> Unit) {
        clickedAction = action
    }

    fun navigate() {
        clickedAction?.invoke()
    }



}
