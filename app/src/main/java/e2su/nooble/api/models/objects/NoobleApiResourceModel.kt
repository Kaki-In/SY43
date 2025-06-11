package e2su.nooble.api.models.objects

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NoobleApiResourceModel (
    val id: String,
    val name: String,
    val filename: String,
    val sentDate: @ParameterName("sent_date") Instant,
    val sender: String,
    val size: Int,
    val filetype: NoobleApiResourceType
)