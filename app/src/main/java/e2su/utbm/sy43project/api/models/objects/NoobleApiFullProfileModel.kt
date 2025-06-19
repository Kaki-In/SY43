package e2su.utbm.sy43project.api.models.objects

data class NoobleApiFullProfileModel (
    val profile: NoobleApiAccountProfileModel,
    val classes: List<NoobleApiClassModel>,
    val badges: List<NoobleApiBadgeModel>,
    val decoration: NoobleApiDecorationModel?
)
