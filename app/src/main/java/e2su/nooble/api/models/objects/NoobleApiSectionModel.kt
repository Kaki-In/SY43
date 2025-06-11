package e2su.nooble.api.models.objects

import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiSectionModel<data_type:NoobleApiSectionDataModel> (
    val type: NoobleApiSection,
    val data: data_type
)

@Serializable
abstract class NoobleApiSectionDataModel ()

@Serializable
data class NoobleApiContainerSectionDataModel (
    val isHorizontal: @ParameterName("is_horizontal") Boolean,
    val isWrapping: @ParameterName("is_wrapping") Boolean,
    val children: List<NoobleApiSectionModel<NoobleApiSectionDataModel>>
)
