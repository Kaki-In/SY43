package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class BuyBadgeRequestModel (
    val name: String
)
