package com.example.healthapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.healthapp.ui.Calender.CalendarScreen
import com.example.healthapp.ui.Calender.DayCardDetails
import com.example.healthapp.ui.Trackables.TrackableListScreen
import com.example.healthapp.ui.Trackables.TrackableScreen
import com.example.healthapp.ui.evaluation.EvaluationScreen
import kotlinx.serialization.Serializable

/**
 * Enthalt die Navigation und die Topbar Logik
 * TODO Navigation nicht immer popbackstack, sondern gezielt/Seitentitel auch
 */

sealed interface Route {
    @Serializable
    object Calendar

    @Serializable
    data class Trackables(
        val name: String
    )

    @Serializable
    object TrackableDetails //besserer Name wäre besser

    @Serializable
    object Evaluation


    @Serializable
    data class DayDetails(
        val name: String
    )
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
    val currentRoute = backStackEntry?.destination
    val showBackButton = currentRoute?.route != Route.Calendar::class.qualifiedName
    val showBackText = currentRoute?.route != Route.Calendar::class.qualifiedName

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
                CalendarScreen(
                    onNavigateToDetails = { name ->
                        navController.navigate(Route.DayDetails(name))
                    })

            }
            composable<Route.Trackables> {
                TrackableListScreen(onNavigateToTrackableScreen = { name ->
                    navController.navigate(Route.TrackableDetails)
                })
//            }
//            composable<Route.TrackableDetails> {
//                TrackableScreen(onNavigateBack = { navController.popBackStack() },
//                    onSave = { navController.navigate(Route.Calendar)})
//            } erst wenn trackable ausgelagert in VM
//            composable<Route.DayDetails> {
//                DayCardDetails( //noch zu einem Screen ändern
//                    onNavigateBack = { navController.popBackStack() }
//                   )
//            } erst wenn day aus VM kommt
                composable<Route.Evaluation> {
                    EvaluationScreen()
                }

//
//            composable<Route.Usage> {
//                UsageScreen(
//                    onNavigateToDetails = { name ->
//                        navController.navigate((Route.ReasonDetails(name)))
//                    },
//                    onGoOn = {navController.navigate(Route.LessonsIntroduction)}
//                )
//            }

            }
        }
    }
}



