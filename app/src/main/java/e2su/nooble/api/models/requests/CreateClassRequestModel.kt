package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequestModel (
    @SerialName("name") val name: String,
    @SerialName("description") val description: String
)
