package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetDecorationInfoRequestModel (
    val decoration: String
)
