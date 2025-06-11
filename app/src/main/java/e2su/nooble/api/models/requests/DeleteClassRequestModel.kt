package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteClassRequestModel (
    val classId: @ParameterName("class_id") String
)
