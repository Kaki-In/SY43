package e2su.utbm.sy43project.api.models.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NoobleApiRole() {
    @SerialName("admin")
    ROLE_ADMIN,

    @SerialName("teacher")
    ROLE_TEACHER,

    @SerialName("teacher_admin")
    ROLE_TEACHER_ADMIN,

    @SerialName("student")
    ROLE_STUDENT
}

