package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModifyDecorationRequestModel (
    @SerialName("decoration_id")
    val decorationId: String,

    val name: String,
    val price: Int,

    @SerialName("image_id")
    val imageId: String
)
