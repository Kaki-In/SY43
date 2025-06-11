package e2su.nooble.api.models.responses

import e2su.nooble.api.models.objects.NoobleApiBadgeModel
import kotlinx.serialization.Serializable

@Serializable
class BuyDecorationResponseModel (
    val newQuota: @ParameterName("new_quota") String
)

