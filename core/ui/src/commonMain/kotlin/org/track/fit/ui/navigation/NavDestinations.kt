package org.track.fit.ui.navigation

enum class NavDestinations(
    val route:String
){
    Splash(route = "Splash"),
    SignIn(route = "SignIn"),
    SignUp(route = "SignUp"),
    Auth(route = "Auth"),
    Registration(route = "Registration"),
    Home(route = "Home"),
    Report(route = "Report"),
    Account(route = "Account"),
    Track(route = "Track"),
}

enum class NestedRoute{
    AuthNavigation,
    BottomNavigation
}

const val SharedAxisSlideDiStance = 400
const val SharedAxisDuration = 280