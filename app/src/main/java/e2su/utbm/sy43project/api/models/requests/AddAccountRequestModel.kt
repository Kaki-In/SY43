package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddAccountRequestModel (
    @SerialName("mail") val mail: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String
)


