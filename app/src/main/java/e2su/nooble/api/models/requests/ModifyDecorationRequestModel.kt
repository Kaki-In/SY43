package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ModifyDecorationRequestModel (
    val decorationId: @ParameterName("decoration_id") String,
    val name: String,
    val price: Int,
    val imageId: @ParameterName("image_id") String
)
