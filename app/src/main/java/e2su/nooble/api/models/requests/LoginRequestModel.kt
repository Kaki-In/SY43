package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel (
    val username: String,
    val password: String
)
