package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiBadgeModel (
    @SerialName("name") val name: String,
    @SerialName("level") val level: Int,
    @SerialName("price") val price: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String
)
