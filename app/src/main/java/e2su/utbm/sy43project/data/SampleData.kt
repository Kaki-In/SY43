package e2su.utbm.sy43project.data

import e2su.nooble.models.ProfileModel
import e2su.nooble.models.ClassModel
import e2su.utbm.sy43project.R
import e2su.nooble.models.ShopItemModel
import e2su.nooble.models.ShopItemType
import e2su.nooble.models.BorderAnimationType
import e2su.nooble.models.ProfileBorderData
import androidx.compose.ui.graphics.Color

object SampleData {
    val borderData = mapOf(
        "1" to ProfileBorderData(
            borderType = BorderAnimationType.STATIC,
            colors = listOf(Color(0xFFFFD700)) // Couleur or
        ),
        "2" to ProfileBorderData(
            borderType = BorderAnimationType.COLOR_SHIFT,
            colors = listOf(
                Color(0xFFFF0000), // Rouge
                Color(0xFFFF7F00), // Orange
                Color(0xFFFFFF00), // Jaune
                Color(0xFF00FF00), // Vert
                Color(0xFF0000FF), // Bleu
                Color(0xFF4B0082), // Indigo
                Color(0xFF9400D3)  // Violet
            )
        ),
        "3" to ProfileBorderData(
            borderType = BorderAnimationType.FLAME,
            colors = listOf(Color(0xFFFF5722), Color(0xFFFF9800))
        )
    )

    val badgeData = mapOf(
        "4" to "Passer 2h sur l'application",
        "5" to "Compléter 10 exercices"
    )

    val sampleClasses = mutableListOf(
        ClassModel(1, "Mathématiques", R.drawable.woof, "Description maths"),
        ClassModel(2, "Physique", R.drawable.woof, "Description physique"),
        ClassModel(3, "Informatique", R.drawable.woof, "Description info")
    )

    val sampleProfile = ProfileModel(
        id = 1,
        name = "Doe",
        surname = "John",
        image = R.drawable.book,
        mail = "john.doe@utbm.fr",
        description = "Étudiant en TC",
        isAdmin = false,
        classes = mutableListOf(sampleClasses[0], sampleClasses[1])
    )

    val sampleProfiles = listOf(
        sampleProfile,
        ProfileModel(
            id = 2,
            name = "Smith",
            surname = "Alice",
            image = R.drawable.book,
            mail = "alice.smith@utbm.fr",
            description = "Professeur",
            isAdmin = true,
            classes = mutableListOf(sampleClasses[1], sampleClasses[2])
        ),
        ProfileModel(
            id = 3,
            name = "Martin",
            surname = "Bob",
            image = R.drawable.book,
            mail = "bob.martin@utbm.fr",
            description = "Étudiant en FISE",
            isAdmin = false,
            classes = mutableListOf(sampleClasses[0], sampleClasses[2])
        )
    )

    val previewProfile = sampleProfile

    val shopItems = listOf(
        ShopItemModel(
            id = "1",
            name = "Contour Doré",
            image = R.drawable.profile,
            price = 100.0,
            isEligible = true,
            isOwned = false,
            itemType = ShopItemType.PROFILE_BORDER
        ),
        ShopItemModel(
            id = "2",
            name = "Contour Arc-en-ciel",
            image = R.drawable.profile,
            price = 150.0,
            isEligible = true,
            isOwned = false,
            itemType = ShopItemType.PROFILE_BORDER
        ),
        ShopItemModel(
            id = "3",
            name = "Contour Flammes",
            image = R.drawable.profile,
            price = 200.0,
            isEligible = true,
            isOwned = false,
            itemType = ShopItemType.PROFILE_BORDER
        ),
        ShopItemModel(
            id = "4",
            name = "Badge Explorateur",
            image = R.drawable.book,
            price = 50.0,
            isEligible = false,
            isOwned = false,
            itemType = ShopItemType.BADGE
        ),
        ShopItemModel(
            id = "5",
            name = "Badge Vétéran",
            image = R.drawable.woof,
            price = 75.0,
            isEligible = true,
            isOwned = true,
            itemType = ShopItemType.BADGE
        )
    )

    fun ShopItemModel.getBorderData(): ProfileBorderData? {
        return if (this.itemType == ShopItemType.PROFILE_BORDER) {
            borderData[this.id]
        } else {
            null
        }
    }

    fun ShopItemModel.getBadgeCondition(): String? {
        return if (this.itemType == ShopItemType.BADGE) {
            badgeData[this.id]
        } else {
            null
        }
    }
}