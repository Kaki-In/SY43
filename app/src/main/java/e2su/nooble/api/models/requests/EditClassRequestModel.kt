package e2su.nooble.api.models.requests

import e2su.nooble.api.models.objects.NoobleApiSectionDataModel
import e2su.nooble.api.models.objects.NoobleApiSectionModel
import kotlinx.serialization.Serializable

@Serializable
data class EditClassRequestModel (
    val classId: @ParameterName("id") String,
    val title: String,
    val description: String,
    val content: NoobleApiSectionModel<NoobleApiSectionDataModel>
)
