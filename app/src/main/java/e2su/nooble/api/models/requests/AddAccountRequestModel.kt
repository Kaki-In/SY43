package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddAccountRequestModel (
    val mail: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String
)


