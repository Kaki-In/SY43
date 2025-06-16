package e2su.utbm.sy43project.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateClassResponseModel (
    @SerialName("new_class") val newClassId: String
)
