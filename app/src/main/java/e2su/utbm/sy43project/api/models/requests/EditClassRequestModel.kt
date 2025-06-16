package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class EditClassRequestModel (
    @SerialName("id") val classId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("content") val content: JsonObject
)
