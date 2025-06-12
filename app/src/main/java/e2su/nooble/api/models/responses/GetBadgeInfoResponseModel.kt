package e2su.nooble.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetBadgeInfoResponseModel (
    @SerialName("max_level")
    val maxLevel: Int,

    val title: String,
    val description: String
)
