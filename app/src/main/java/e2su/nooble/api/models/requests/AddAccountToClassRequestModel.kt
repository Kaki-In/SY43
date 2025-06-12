package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddAccountToClassRequestModel (
    @SerialName("user_id")
    val userId: String,
    @SerialName("class_id")
    val classId: String
)
