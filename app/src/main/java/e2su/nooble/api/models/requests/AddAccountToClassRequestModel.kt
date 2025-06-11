package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddAccountToClassRequestModel (
    val userId: @ParameterName("user_id") String,
    val classId: @ParameterName("class_id") String
)
