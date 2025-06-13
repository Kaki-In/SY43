package e2su.nooble.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BuyBadgeResponseModel (
    @SerialName("new_quota") val newQuota: Int,
    @SerialName("new_level") val newLevel: Int
)
