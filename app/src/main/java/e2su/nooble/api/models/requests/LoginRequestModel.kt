package e2su.nooble.api.models.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel (
    @SerialName("username") val username: String,
    @SerialName("password") val password: String
)
