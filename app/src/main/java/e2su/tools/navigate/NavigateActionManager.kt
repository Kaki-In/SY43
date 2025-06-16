package e2su.tools.navigate

class NavigateActionManager {
    private var clickedAction: (() -> Unit)? = null

    fun setClickedAction(action: () -> Unit) {
        clickedAction = action
    }

    fun navigate() {
        clickedAction?.invoke()
    }

}

class NeedsIdNavigateActionManager<IdType> {
    private var clickedAction: ((IdType) -> Unit)? = null

    fun setClickedAction(action: (IdType) -> Unit) {
        clickedAction = action
    }

    fun navigate(objId: IdType) {
        clickedAction?.invoke(objId)
    }

}
