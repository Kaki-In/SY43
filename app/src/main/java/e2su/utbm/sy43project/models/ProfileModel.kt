package e2su.utbm.sy43project.models

import android.media.Image

data class ProfileModel
(
    var id: Int,
    var name: String,
    var surname: String,
    var image: Image,
    var mail: String,
    var description: String,
    var isAdmin: Boolean,
    var classes: MutableList<ClassModel>
)

