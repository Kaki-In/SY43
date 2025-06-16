package e2su.utbm.sy43project.api.models.responses

import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListBadgesResponseModel (
    @SerialName("reached") val reached: List<NoobleApiBadgeModel>,
    @SerialName("unreached") val unreached: List<NoobleApiBadgeModel>
)

