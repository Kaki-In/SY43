package e2su.nooble.models

data class ShopItemModel(
    val id: String,
    val name: String,
    val price: Double,
    val isOwned: Boolean
)