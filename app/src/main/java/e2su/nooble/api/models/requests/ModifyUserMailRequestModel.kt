package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserMailRequestModel (
    @SerialName("user_id")
    val userId: String,
    val mail: String
)
