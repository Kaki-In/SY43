package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
class GetBadgeInfosResponseModel (
    val maxLevel: @ParameterName("max_level") Int,
    val title: String,
    val description: String
)
