package org.track.fit.data.repository.preferences

enum class PersonalPreferences(
    val field:String
) {
    Age(
        field = "age"
    ),
    Height(
        field = "height"
    ),
    Weight(
        field = "weight"
    ),
    Gender(
        field = "gender"
    ),
    StepsGoal(
        field = "steps_goal"
    )
}