package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiResourceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloadResourceRequestModel (
    @SerialName("id") val id: String,
    @SerialName("type") val type: NoobleApiResourceType
)
