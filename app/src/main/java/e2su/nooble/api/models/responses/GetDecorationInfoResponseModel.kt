package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class GetDecorationInfoResponseModel (
    val name: String,
    val price: String,
    val image: String
)
