package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetAccountProfileRequestModel (
    val userId: @ParameterName("user_id") String
)
