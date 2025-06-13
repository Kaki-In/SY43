package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBadgeThumbnailRequestModel (
    @SerialName("name") val name: String,
    @SerialName("level") val level: Int
)
