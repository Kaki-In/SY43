package e2su.nooble.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInfoResponseModel (
    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String
)
