package e2su.utbm.sy43project.data

import e2su.nooble.models.ProfileModel
import e2su.nooble.models.ClassModel
import e2su.utbm.sy43project.R

object SampleData {
    val sampleClasses = mutableListOf(
        ClassModel(1, "Mathématiques", R.drawable.woof, "Description maths"),
        ClassModel(2, "Physique", R.drawable.woof,"Description physique"),
        ClassModel(3, "Informatique", R.drawable.woof,"Description info")
    )

    val sampleProfile = ProfileModel(
        id = 1,
        name = "Doe",
        surname = "John",
        image = R.drawable.book,
        mail = "john.doe@utbm.fr",
        description = "Étudiant en TC",
        isAdmin = false,
        classes = mutableListOf(sampleClasses[0], sampleClasses[1])
    )

    val sampleProfiles = listOf(
        sampleProfile,
        ProfileModel(
            id = 2,
            name = "Smith",
            surname = "Alice",
            image = R.drawable.book,
            mail = "alice.smith@utbm.fr",
            description = "Professeur",
            isAdmin = true,
            classes = mutableListOf(sampleClasses[1], sampleClasses[2])
        ),
        ProfileModel(
            id = 3,
            name = "Martin",
            surname = "Bob",
            image = R.drawable.book,
            mail = "bob.martin@utbm.fr",
            description = "Étudiant en FISE",
            isAdmin = false,
            classes = mutableListOf(sampleClasses[0], sampleClasses[2])
        )
    )

    val previewProfile = sampleProfile
}