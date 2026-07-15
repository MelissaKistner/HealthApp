package com.example.healthapp.data.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.healthapp.ui.Route

enum class TabBarItem(
    val title: String,
    val route: Route,
    val icon: ImageVector
) {
    CALENDER(
        title = "Kalender",
        route = Route.Calendar,
        icon = Icons.Default.GridView
    ),

    CATEGORIES(
    title = "Kategorien", //noch verändern, besserer Name
    route = Route.Trackables(name = ""),
    icon = Icons.Default.GridView
    ),

//    EVALUATION(
//        title = "Auswertung", //noch verändern, besserer Name
//        route = Route.Evaluation,
//        icon = Icons.Default.GridView
//    ),


}