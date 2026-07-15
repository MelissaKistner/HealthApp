package com.example.healthapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthapp.data.enums.TabBarItem
import com.example.healthapp.ui.trackables.TrackableListScreen
import com.example.healthapp.ui.calendar.CalendarGrid
import com.example.healthapp.ui.evaluation.EvaluationScreen
import com.example.healthapp.ui.trackables.TrackableScreen
import kotlinx.serialization.Serializable

/**
 * Enthalt die Navigation und die Topbar Logik
 * TODO Nav zu Evaluation geht nicht, stürzt ab
 * TODO Navigation nicht immer popbackstack, sondern gezielt/Seitentitel auch
 */

sealed interface Route {
    @Serializable
    object Calendar: Route

    @Serializable
    data class Trackables(
        val name: String
    ): Route

    @Serializable
    object TrackableDetails: Route //besserer Name wäre besser

    @Serializable
    object Evaluation: Route


    @Serializable
    data class DayDetails(
        val name: String //epochDay: Long später
    ) : Route
}

/**
 * TODO onClickBack zu konkreten Screens zurückführen, nicht nur popbackstack
 * TODO Pagetitle besser pro Seite verscheiden?
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStart() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState() //sorgen dafür, dass ich im Homescreen den Bacbutton ausblenden kann
//    val currentRoute = backStackEntry?.destination
//    val showBackButton = currentRoute?.route != Route.Calendar::class.qualifiedName
//    val showBackText = currentRoute?.route != Route.Calendar::class.qualifiedName
    var selectedTab by rememberSaveable { mutableStateOf(TabBarItem.CALENDER) }
    val items = TabBarItem.entries
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .graphicsLayer {
                        shadowElevation = 6.dp.toPx()
                        shape = RoundedCornerShape(14.dp)
                        clip = false
                        ambientShadowColor = Color.White.copy(alpha = 0.90f)
                        spotShadowColor = Color.White.copy(alpha = 0.40f)
                    },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                onClick = { navController.navigate(Route.Calendar) }
            ) {
                Icon(Icons.Default.Home, contentDescription = "Home Icon")
            }
        },
        bottomBar = {

                NavigationBar {
                    TabBarItem.entries.forEach { item ->
                        NavigationBarItem(
                            selected = selectedTab == item,
                            onClick = {
                                selectedTab = item
                                navController.navigate(item.route) {
                                    popUpTo(Route.Calendar)
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                item.title
                                item.icon
                            },
                            label = { Text(text = item.title) }
                        )
                    }
                }


        }
//        topBar = {
//            TopAppBar(
//                title = {if (showBackText) FreeText(stringResource(R.string.backButton_title)) else FreeText("")},
//                navigationIcon = {
//                    if (showBackButton) {
//                        IconButton(
//                            onClick = {
//                                navController.popBackStack()
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                                contentDescription = "Zurück Icon"
//                            )
//                        }
//                    }
//                }
//
//
//            )
//        }
    )
    { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.Calendar,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<Route.Calendar> {
                CalendarGrid(
                    onNavigateToDetails = { name ->
                        navController.navigate(Route.DayDetails(name))
                    })

            }
            composable<Route.Trackables> {
                TrackableListScreen(onNavigateToTrackableScreen = { name ->
                    navController.navigate(Route.TrackableDetails)
                })
            }
            composable<Route.TrackableDetails> {
                TrackableScreen(onNavigateBack = { navController.popBackStack() },
                    onSave = { navController.navigate(Route.Calendar)}, trackableName = "Migräne")
            } //erst wenn trackable ausgelagert in VM
//            composable<Route.DayDetails> {
//                DayCardDetails( //noch zu einem Screen ändern
//                    onNavigateBack = { navController.popBackStack() }
//                   )
//            } erst wenn day aus VM kommt
                composable<Route.Evaluation> {
                    EvaluationScreen()
                }
            }
        }
    }




