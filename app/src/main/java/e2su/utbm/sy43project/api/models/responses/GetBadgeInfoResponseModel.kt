package e2su.utbm.sy43project.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetBadgeInfoResponseModel (
    @SerialName("max_level") val maxLevel: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String
)
