package e2su.utbm.sy43project.ui.navigation.shop

private enum class GrantKind
{
    KIND_BADGE,
    KIND_DECORATION
}

sealed class ShopNavRoute(route: GrantKind) {
    private val _route = route

    object Badges: ShopNavRoute(GrantKind.KIND_BADGE)
    object Decorations: ShopNavRoute(GrantKind.KIND_DECORATION)

    class BuyBadge(val badgeName: String): ShopNavRoute(GrantKind.KIND_BADGE)
    class BuyDecoration(val decorationName: String): ShopNavRoute(GrantKind.KIND_DECORATION)

    fun isBadge(): Boolean
    {
        return _route == GrantKind.KIND_BADGE
    }
}
