package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiSafeModel (
    @SerialName("quota") val quota: Int,
    @SerialName("decorations") val decorations: List<String>,
    @SerialName("badges") val badges: List<Pair<String, Int>>
)
