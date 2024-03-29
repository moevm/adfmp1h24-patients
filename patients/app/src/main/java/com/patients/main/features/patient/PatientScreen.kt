package com.patients.main.features.patient

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.patients.main.PatientRoutes
import com.patients.main.Routes
import com.patients.main.core.navigation.NavItem
import com.patients.main.core.ui.bottom_nav_bar.BottomNavBar
import com.patients.main.features.patient.home_screen.presentation.PatientHomeScreen
import com.patients.main.features.patient.surveys_screen.presentation.PatientSurveysScreen
import com.patients.main.features.patient.take_survey_screen.presentation.PatientTakeSurveyScreen

val navItems: List<NavItem<ImageVector>> = listOf(
    NavItem(
        route = Routes.Home.route,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    NavItem(
        route = Routes.Other.route,
        selectedIcon = Icons.Filled.CheckCircle,
        unselectedIcon = Icons.Outlined.CheckCircle
    )
)

@Composable
fun PatientScreen(
    modifier: Modifier = Modifier,
    patientId: String,
    patientViewModel: PatientViewModel = viewModel(),
) {
    val patient = patientViewModel.getPatient(patientId)
    val surveys = patientViewModel.getSurveys(patientId)
    val navController = rememberNavController()
    Scaffold (
        modifier = modifier,
        bottomBar = {
            BottomNavBar(
                navController = navController,
                navItems = navItems,
                visibleScreens = listOf(Routes.Home.route, Routes.Other.route)
            )
        },
    ) {paddingValues ->
        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(
                route = Routes.Home.route
            ) {
                PatientHomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    patientName = patient.name,
                    surveys = surveys.filter { !it.completed }
                )
            }

            composable(
                route = Routes.Other.route
            ) {
                PatientSurveysScreen(
                    modifier = Modifier.padding(paddingValues),
                    surveys = surveys
                )
            }

            composable(
                route = PatientRoutes.TakeSurvey.route + "/{${PatientRoutes.TakeSurvey.argName}}",
                arguments = listOf(
                    navArgument(PatientRoutes.TakeSurvey.argName) {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) { entry ->
                PatientTakeSurveyScreen(
                    modifier = Modifier.padding(paddingValues),
                    surveyCard = surveys.filter {
                        it.id == (entry.arguments?.getString(PatientRoutes.TakeSurvey.argName) ?: "0")
                    }[0],
                    onResetSurveyAnswers = patientViewModel::resetSurveyAnswers,
                    onSubmitSurvey = patientViewModel::onSubmitSurvey,
                    onSubmitQuestionAnswer = patientViewModel::onSubmitQuestionAnswer,
                    navController = navController
                )
            }
        }
    }
}