package e2su.utbm.sy43project.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDecorationInfoResponseModel (
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("image") val image: String
)
