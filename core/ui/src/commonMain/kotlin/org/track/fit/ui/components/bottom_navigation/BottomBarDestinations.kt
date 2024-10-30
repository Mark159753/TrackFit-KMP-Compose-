package org.track.fit.ui.components.bottom_navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import trackfit.core.common.generated.resources.Res
import trackfit.core.common.generated.resources.account_ic
import trackfit.core.common.generated.resources.bottom_nav_account
import trackfit.core.common.generated.resources.bottom_nav_home
import trackfit.core.common.generated.resources.bottom_nav_report
import trackfit.core.common.generated.resources.bottom_nav_track
import trackfit.core.common.generated.resources.home_ic
import trackfit.core.common.generated.resources.location_ic
import trackfit.core.common.generated.resources.report_ic

enum class BottomBarDestinations(
    val icon:DrawableResource,
    val title: StringResource
) {

    Home(
        icon = Res.drawable.home_ic,
        title = Res.string.bottom_nav_home
    ),

    Track(
        icon = Res.drawable.location_ic,
        title = Res.string.bottom_nav_track
    ),

    Report(
        icon = Res.drawable.report_ic,
        title = Res.string.bottom_nav_report
    ),

    Account(
        icon = Res.drawable.account_ic,
        title = Res.string.bottom_nav_account
    )

}