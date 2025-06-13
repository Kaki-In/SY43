package e2su.nooble.api.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDecorationInfoResponseModel (
    @SerialName("name") val name: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String
)
