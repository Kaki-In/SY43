package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiResourceType
import kotlinx.serialization.Serializable

@Serializable
data class DownloadResourceRequestModel (
    val id: String,
    val type: NoobleApiResourceType
)
