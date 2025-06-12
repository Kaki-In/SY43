package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.Serializable

@Serializable
class ListBadgesResponseModel (
    val reached: List<NoobleApiBadgeModel>,
    val unreached: List<NoobleApiBadgeModel>
)

