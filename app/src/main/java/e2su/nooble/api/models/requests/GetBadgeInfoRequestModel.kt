package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBadgeInfoRequestModel (
    @SerialName("name") val name: String,
    @SerialName("level") val level: Int
)
