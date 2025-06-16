package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiAccountProfileModel(
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("active_decoration") val activeDecoration: String?,
    @SerialName("active_badges") val activeBadges: List<Pair<String, Int>>,
    @SerialName("description") val description: String,
    @SerialName("role") val role: NoobleApiRole? = null,
    @SerialName("classes") val classes: List<String>? = null
)