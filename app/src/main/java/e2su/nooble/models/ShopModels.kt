package e2su.nooble.models

import androidx.compose.ui.graphics.Color

// Modèle de base pour les articles de la boutique
data class ShopItemModel(
    val id: String,
    val name: String,
    val image: Int? = null,
    val price: Double,
    val isEligible: Boolean,
    val isOwned: Boolean,
    val itemType: ShopItemType
)

// Types d'articles disponibles dans la boutique
enum class ShopItemType {
    BADGE,
    PROFILE_BORDER
}

// Modèle pour les contours animés de profil
data class ProfileBorderData(
    val borderType: BorderAnimationType,
    val colors: List<Color>? = null,
    val animationDuration: Int = 3000 // milliseconds
)

// Types d'animations pour les contours
enum class BorderAnimationType {
    STATIC,
    COLOR_SHIFT,
    FLAME,
    PARTICLES,
    ROTATING
}

data class BorderShopItem(
    val shopItem: ShopItemModel,
    val borderData: ProfileBorderData
)