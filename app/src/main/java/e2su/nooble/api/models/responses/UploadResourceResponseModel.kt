package e2su.nooble.api.models.responses

import com.squareup.wire.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UploadResourceResponseModel (
    val newFileId: @ParameterName("new_file") String,
    val date: Instant,
    val size: Int
)

