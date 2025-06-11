package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiResourceType
import kotlinx.serialization.Serializable

@Serializable
data class MarkThreadAsReadRequestModel (
    val activities: List<String>
)
