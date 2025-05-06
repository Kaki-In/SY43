package e2su.nooble.models

import java.util.Date

data class ActivityModel (
    var title: String,
    var date: Date,
    var sender: ProfileModel
)

