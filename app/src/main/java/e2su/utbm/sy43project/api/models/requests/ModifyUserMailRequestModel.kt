package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserMailRequestModel (
    @SerialName("user_id") val userId: String,
    @SerialName("mail") val mail: String
)
