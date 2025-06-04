package e2su.nooble.models

data class ProfileWithBadgesAndBorders(
    val profile: ProfileModel,
    val badges: List<BadgeShopItem>,
    val borders: List<BorderShopItem>
)