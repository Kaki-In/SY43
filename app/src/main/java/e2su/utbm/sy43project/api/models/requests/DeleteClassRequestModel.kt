package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteClassRequestModel (
    @SerialName("class_id") val classId: String
)
