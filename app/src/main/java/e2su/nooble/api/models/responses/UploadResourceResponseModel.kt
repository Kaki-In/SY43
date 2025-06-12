package e2su.nooble.api.models.responses

import com.squareup.wire.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadResourceResponseModel (
    @SerialName("new_file")
    val newFileId: String,
    val date: Instant,
    val size: Int
)

