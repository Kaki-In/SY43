package e2su.nooble.api.models.objects

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiClassModel (
    @SerialName("id") val id: String,
    @SerialName("content") val content: NoobleApiSectionModel<NoobleApiSectionDataModel>,
    @SerialName("description") val description: String,
    @SerialName("lastModification") val lastModification: @ParameterName("last_modification") Instant,
    @SerialName("lastModifier") val lastModifier: @ParameterName("last_modifier") String,
    @SerialName("name") val name: String
)