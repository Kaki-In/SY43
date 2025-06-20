@file:UseSerializers(InstantSerializer::class)

package e2su.utbm.sy43project.api.models.responses

import e2su.utbm.sy43project.api.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class UploadResourceResponseModel (
    @SerialName("new_file") val newFileId: String,
    @SerialName("date") val date: Instant,
    @SerialName("size") val size: Int
)

