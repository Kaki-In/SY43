package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchClassRequestModel (
    @SerialName("pattern") val pattern: String,
    @SerialName("offset") val offset: Int,
    @SerialName("count") val count: Int
)


