package e2su.nooble.api.models.responses

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResourceResponseModel (
    @SerialName("new_file") val newFileId: String,
    @SerialName("date") val date: Instant,
    @SerialName("size") val size: Int
)

