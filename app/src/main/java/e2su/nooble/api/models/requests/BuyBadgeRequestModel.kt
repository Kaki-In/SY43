package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyBadgeRequestModel (
    @SerialName("name") val name: String
)
