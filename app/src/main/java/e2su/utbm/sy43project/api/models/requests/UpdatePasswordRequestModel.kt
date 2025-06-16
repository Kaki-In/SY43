package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePasswordRequestModel (
    @SerialName("last_password") val lastPassword: String,
    @SerialName("new_password") val newPassword: String
)


