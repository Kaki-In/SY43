package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAccountRequestModel (
    val userId: @ParameterName("user_id") String
)
