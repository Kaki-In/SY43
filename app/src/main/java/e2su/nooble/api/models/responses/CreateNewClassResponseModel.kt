package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreateNewClassResponseModel (
    val newClassId: @ParameterName("new_class") String
)
