package e2su.nooble.api.models.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreateNewAccountResponseModel (
    val newAccountId: @ParameterName("new_account") String
)
