package e2su.utbm.sy43project.viewmodels

import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel

sealed class OpenPurchasableItem {
    object Closed: OpenPurchasableItem()
    class Badge(val badgeModel: NoobleApiBadgeModel): OpenPurchasableItem()
    class Decoration(val decorationModel: NoobleApiDecorationModel): OpenPurchasableItem()
}
