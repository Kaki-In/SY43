package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoobleApiSection {
    @SerialName("container")
    SECTION_CONTAINER,

    @SerialName("rich-text")
    SECTION_RICH_TEXT,

    @SerialName("raw-text")
    SECTION_RAW_TEXT,

    @SerialName("image")
    SECTION_IMAGE,

    @SerialName("audio")
    SECTION_AUDIO,

    @SerialName("video")
    SECTION_VIDEO,

    @SerialName("integration")
    SECTION_INTEGRATION,

    @SerialName("activity")
    SECTION_ACTIVITY,

    @SerialName("file")
    SECTION_FILE
}
