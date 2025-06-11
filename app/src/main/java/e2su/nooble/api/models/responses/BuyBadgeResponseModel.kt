package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
class BuyBadgeResponseModel (
    val newQuota: @ParameterName("new_quota") Int,
    val newLevel: @ParameterName("new_level") Int
)
