package e2su.nooble.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiSectionModel<data_type:NoobleApiSectionDataModel> (
    @SerialName("type") val type: NoobleApiSection,
    @SerialName("data") val data: data_type
)

@Serializable
abstract class NoobleApiSectionDataModel ()

@Serializable
data class NoobleApiContainerSectionDataModel (
    @SerialName("is_horizontal") val isHorizontal: Boolean,
    @SerialName("is_wrapping")val isWrapping: Boolean,
    @SerialName("children") val children: List<NoobleApiSectionModel<NoobleApiSectionDataModel>>
)
