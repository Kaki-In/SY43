package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyBadgeRequestModel (
    @SerialName("name") val name: String
)
