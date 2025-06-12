package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetBadgeInfoRequestModel (
    val name: String,
    val level: Int
)
