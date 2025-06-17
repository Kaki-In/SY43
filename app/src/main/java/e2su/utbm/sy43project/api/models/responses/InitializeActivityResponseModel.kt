package e2su.utbm.sy43project.api.models.responses;

import kotlinx.serialization.SerialName;
import kotlinx.serialization.Serializable;

@Serializable
data class InitializeActivityResponseModel (
    @SerialName("new_file") val newFileId: String
)
