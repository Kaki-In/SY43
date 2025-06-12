package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequestModel (
    val username: String
)
