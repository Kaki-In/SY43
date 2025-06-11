package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteDecorationRequestModel (
    val decorationId: @ParameterName("decoration_id") String
)
