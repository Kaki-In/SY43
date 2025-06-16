package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetThreadRequestModel (
    @SerialName("notreadonly") val notReadOnly: Boolean,
    @SerialName("count") val count: Int,
    @SerialName("offset") val offset: Int
)
