package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiDecorationModel (
    val id: String,
    val price: Int,
    val name: String,
    val imageId: @ParameterName("image") String
)
