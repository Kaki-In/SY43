package e2su.utbm.sy43project.models

import java.util.Date

data class ActivityModel (
    var title: String,
    var date: Date,
    var sender: ProfileModel
)

