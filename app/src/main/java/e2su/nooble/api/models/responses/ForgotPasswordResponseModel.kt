package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponseModel (
    val firstName: @ParameterName("first_name") String,
    val lastName: @ParameterName("last_name") String
)
