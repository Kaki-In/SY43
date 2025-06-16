package e2su.utbm.sy43project.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDecorationRequestModel (
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("image_id") val imageId: String
)
