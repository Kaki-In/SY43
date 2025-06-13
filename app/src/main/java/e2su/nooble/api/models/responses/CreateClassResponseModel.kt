package e2su.nooble.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateClassResponseModel (
    @SerialName("new_class") val newClassId: String
)
