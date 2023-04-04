package com.example.touska.navigation

sealed class MainNavigation(val route : String) {
    object Home : MainNavigation("Home")
    object Report : MainNavigation("Report")
    object Setting : MainNavigation("Setting")

    object Bloc : MainNavigation("bloc")
    object Floor : MainNavigation("floor")

    object Unit : MainNavigation("unit")

    object Post : MainNavigation("post")

    object Activity : MainNavigation("activity")

    object Contract : MainNavigation("contract")

    fun withArgs(vararg args:  String ) : String {
        return  buildString {
            append(route)
            args.forEach {arg->
              append("/${arg}")
            }
        }
    }

}
