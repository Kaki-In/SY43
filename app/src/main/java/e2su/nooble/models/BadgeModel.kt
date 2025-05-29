package e2su.nooble.models;

import android.media.Image

data class BadgeShopItem(
    val shopItem: ShopItemModel,
    val condition: String, // Description de la condition pour débloquer le badge
    val conditionType: BadgeConditionType
)

enum class BadgeConditionType {
    TIME_SPENT,
    ACHIEVEMENTS,
    LEVEL_REACHED,
    SPECIAL_EVENT
}