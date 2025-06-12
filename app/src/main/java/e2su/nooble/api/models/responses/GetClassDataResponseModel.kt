package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiSectionDataModel
import e2su.nooble.api.models.objects.NoobleApiSectionModel
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GetClassDataResponseModel (
    val content: NoobleApiSectionModel<NoobleApiSectionDataModel>,
    val description: String,
    val lastModification: @ParameterName("last_modification") Instant,
    val lastModifier: @ParameterName("last_modifier") String,
    val name: String
)
