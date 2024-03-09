package com.example.helloworld.features.patients.surveys_screen.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun PatientSurveysScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Column(modifier = modifier) {
        TopNavBar(navController = navController)

        NavHost(navController = navController, startDestination = Screen.ClosedSurveys.route) {
            composable(route = Screen.ClosedSurveys.route) {
                PatientClosedSurveysScreen()
            }

            composable(route = Screen.ExpectingSurveys.route) {
                PatientExpectingSurveysScreen()
            }
        }

    }


}

@Composable
private fun TopNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val colors = listOf(
        Color(0xFFD9494C),
        Color.Black
    )
    var color1 by remember {
        mutableStateOf(colors[0])
    }
    var color2 by remember {
        mutableStateOf(colors[1])
    }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(
            modifier = Modifier,
            onClick = {
                navController.navigate(Screen.ClosedSurveys.route)
                color1 = colors[0]
                color2 = colors[1]
            }
        ) {
            Text(
                text = "Закрытые",
                color = color1
            )
        }
        TextButton(onClick = {
            navController.navigate(Screen.ExpectingSurveys.route)
            color1 = colors[1]
            color2 = colors[0]
        }) {
            Text(
                text = "Непроверенные",
                color = color2
            )
        }
    }
}