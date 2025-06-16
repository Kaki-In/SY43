package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MarkThreadAsReadRequestModel (
    @SerialName("activities") val activities: List<String>
)
