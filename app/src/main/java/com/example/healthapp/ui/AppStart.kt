//package com.example.healthapp.ui
//
//
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LargeFloatingActionButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SmallFloatingActionButton
//import androidx.compose.material3.FreeText
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.androidabschlussprojekt_melissakistner.R
//import com.example.androidabschlussprojekt_melissakistner.ui.home.HomeScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.LessonsIntroductionScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.reasons.IronyReasonDetails
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.reasons.UsageScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.recognition.ChooseLessonTypeScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.recognition.contradiction.ContradictionExplanationScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.recognition.lesson.SceneLessonScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.lesson.recognition.contradiction.ContradictionLessonScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.progress.ProgressScreen
//import com.example.androidabschlussprojekt_melissakistner.ui.quiz.QuizScreen
//import kotlinx.serialization.Serializable
//
///**
// * Enthalt die Navigation und die Topbar Logik
// * TODO Navigation nicht immer popbackstack, sondern gezielt/Seitentitel auch
// */
//
//sealed interface Route {
//    @Serializable
//    object Home
//
//    @Serializable
//    object LessonsIntroduction
//
//    @Serializable
//    object ContradictionExplanation
//    @Serializable
//    object ChooseLessonType
//
//    @Serializable
//    object ContradictionExamples
//
//    @Serializable
//    object ContradictionScenes
//
//    @Serializable
//    object Lesson
//
//    @Serializable
//    object LessonScene
//
//    @Serializable
//    object Usage
//
//    @Serializable
//    data class ReasonDetails( //data class, weil ScreenName übergeben wird, um anhand dessen die richtigen Infos im nächsten Screen laden zu können
//        val name: String
//    )
//
//    @Serializable
//    object Quiz
//
//    @Serializable
//    object Progress
//}
//
///**
// * TODO onClickBack zu konkreten Screens zurückführen, nicht nur popbackstack
// * TODO Pagetitle besser pro Seite verscheiden?
// *
// */
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppStart() {
//    val navController = rememberNavController()
//    val backStackEntry by navController.currentBackStackEntryAsState() //sorgen dafür, dass ich im Homescreen den Bacbutton ausblenden kann
//    val currentRoute = backStackEntry?.destination
//    val showBackButton = currentRoute?.route != Route.Home::class.qualifiedName
//    val showBackText = currentRoute?.route != Route.Home::class.qualifiedName
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                modifier = Modifier
//                    .graphicsLayer {
//                        shadowElevation = 6.dp.toPx()
//                        shape = RoundedCornerShape(14.dp)
//                        clip = false
//                        ambientShadowColor = Color.White.copy(alpha = 0.90f)
//                        spotShadowColor = Color.White.copy(alpha = 0.40f)
//                    },
//                containerColor = MaterialTheme.colorScheme.tertiary,
//                contentColor = MaterialTheme.colorScheme.onTertiary,
//                onClick = { navController.navigate(Route.Home) }
//            ) {
//                Icon(Icons.Default.Home, contentDescription = "Home Icon")
//            }
//        },
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
//    )
//    { paddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = Route.Home,
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            composable<Route.Home> {
//                HomeScreen(
//                    onNavigateToProgress = { navController.navigate(Route.Progress) },
//                    onNavigateToLessonsIntroduction = { navController.navigate(Route.LessonsIntroduction) },
//                    onNavigateToQuiz = { navController.navigate(Route.Quiz) })
//            }
//            composable<Route.Quiz> {
//                QuizScreen(onEndQuiz = { navController.navigate(Route.Home) }) //vielleicht Progress?
//            }
//            composable<Route.LessonsIntroduction> {
//                LessonsIntroductionScreen(
//                    onNavigateToContradictionExplanation = { navController.navigate(Route.ContradictionExplanation) },
//                    onNavigateToUsage = { navController.navigate(Route.Usage) })
//            }
//            composable<Route.ContradictionExplanation> {
//                ContradictionExplanationScreen(onNavigateToLesson = { navController.navigate(Route.ChooseLessonType) })
//            }
//            composable<Route.ChooseLessonType> {
//                ChooseLessonTypeScreen(onContradiction = {navController.navigate(Route.ContradictionExamples)}, onScenes = {navController.navigate(Route.LessonScene)})
//            }
//
//            composable<Route.ContradictionExamples> {
//                ContradictionLessonScreen(onEnd = { navController.navigate(Route.ChooseLessonType) })
//            }
//
//            composable<Route.LessonScene> {
//                SceneLessonScreen()
//            }
//            composable<Route.Usage> {
//                UsageScreen(
//                    onNavigateToDetails = { name ->
//                        navController.navigate((Route.ReasonDetails(name)))
//                    },
//                    onGoOn = {navController.navigate(Route.LessonsIntroduction)}
//                )
//            }
//            composable<Route.ReasonDetails> {
//                IronyReasonDetails(onEndDescription = { navController.popBackStack() })
//            }
//            composable<Route.Progress> {
//                ProgressScreen()
//            }
//        }
//    }
//}
//
//
