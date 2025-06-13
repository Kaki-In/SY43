package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiSectionDataModel
import e2su.nooble.api.models.objects.NoobleApiSectionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditClassRequestModel (
    @SerialName("id") val classId: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("content") val content: NoobleApiSectionModel<NoobleApiSectionDataModel>
)
