package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiDecorationModel (
    @SerialName("id") val id: String,
    @SerialName("price") val price: Int,
    @SerialName("name") val name: String,
    @SerialName("image") val imageId: String
)
