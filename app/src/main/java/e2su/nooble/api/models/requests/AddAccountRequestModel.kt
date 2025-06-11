package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class AddAccountRequestModel (
    val mail: String,
    val firstName: @ParameterName("first_name") String,
    val lastName: @ParameterName("last_name") String
)


