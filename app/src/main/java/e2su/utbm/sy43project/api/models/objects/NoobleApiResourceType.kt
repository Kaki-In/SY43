package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoobleApiResourceType {
    @SerialName("profile icon")
    RESOURCE_TYPE_PROFILE_ICON,

    @SerialName("section file")
    RESOURCE_TYPE_SECTION_FILE,

    @SerialName("decoration banner")
    RESOURCE_TYPE_DECORATION_BANNER,

}