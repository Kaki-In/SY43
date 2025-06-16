@file:UseSerializers(InstantSerializer::class)

package e2su.utbm.sy43project.api.models.objects

import e2su.utbm.sy43project.api.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class NoobleApiResourceModel (
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("filename") val filename: String,
    @SerialName("sent_date") val sentDate: Instant,
    @SerialName("sender") val sender: String,
    @SerialName("size") val size: Int,
    @SerialName("file_type") val filetype: NoobleApiResourceType
)