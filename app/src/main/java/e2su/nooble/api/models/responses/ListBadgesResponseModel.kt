package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListBadgesResponseModel (
    @SerialName("reached") val reached: List<NoobleApiBadgeModel>,
    @SerialName("unreached") val unreached: List<NoobleApiBadgeModel>
)

