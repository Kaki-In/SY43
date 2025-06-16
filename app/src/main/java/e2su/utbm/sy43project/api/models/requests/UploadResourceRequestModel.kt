package e2su.utbm.sy43project.api.models.requests

import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResourceRequestModel (
    @SerialName("name") val name: String,
    @SerialName("type") val type: NoobleApiResourceType
)
