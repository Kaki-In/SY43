package e2su.nooble.api.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateClassRequestModel (
    val name: String,
    val description: String
)
