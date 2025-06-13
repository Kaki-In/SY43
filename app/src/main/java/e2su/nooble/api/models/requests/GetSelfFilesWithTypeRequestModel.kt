package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiResourceType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSelfFilesWithTypeRequestModel (
    @SerialName("type") val type: NoobleApiResourceType
)
