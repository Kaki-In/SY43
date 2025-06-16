package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchAccountRequestModel (
    @SerialName("pattern") val pattern: String,
    @SerialName("offset") val offset: Int,
    @SerialName("count") val count: Int
)


