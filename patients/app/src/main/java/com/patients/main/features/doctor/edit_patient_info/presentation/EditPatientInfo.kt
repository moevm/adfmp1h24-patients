package com.patients.main.features.doctor.edit_patient_info.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.patients.main.R
import com.patients.main.data.PatientDTO
import com.patients.main.data.PatientStatus

@Composable
fun EditPatientInfoScreen(
    navController: NavController,
    patient: PatientDTO,
    modifier: Modifier = Modifier
) {
    val items = listOf(patient.name, patient.diagnosis, patient.status)
    var diagnosis by rememberSaveable { mutableStateOf(patient.diagnosis) }
    var status by rememberSaveable { mutableStateOf(patient.status.name) }
    val statusMap = mapOf<String, PatientStatus>(
        "Вылечился" to PatientStatus.Cured,
        "Ремиссия" to PatientStatus.Remission,
        "Рецидив" to PatientStatus.Relapse
    )
    Column(
        modifier = modifier.padding(bottom = 25.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(start = 15.dp, top = 10.dp)
                .alpha(0.6f)
                .clickable { navController.popBackStack() },
            imageVector = Icons.Filled.Close,
            contentDescription = null
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 9.dp),

            ) {
            itemsIndexed(items) { index, content ->
                if (index == 0) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        fontSize = 20.sp,
                        text = stringResource(R.string.edit_info, content),

                        )
                }
                if (index == 1) {
                    Text(
                        modifier = Modifier.padding(top = 30.dp, bottom = 8.dp),
                        fontSize = 15.sp,
                        text = stringResource(R.string.diagnoses)
                    )
                    OutlinedTextField(
                        value = diagnosis,
                        onValueChange = { input ->
                            diagnosis = input
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                        ),
                    )
                }
                if (index == 2) {
                    Text(
                        modifier = Modifier.padding(top = 30.dp, bottom = 8.dp),
                        fontSize = 15.sp,
                        text = stringResource(R.string.info_status)
                    )
                    for (opt in statusMap.keys) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                                .fillMaxWidth()
                                .clickable { status = opt }
                        ) {
                            RadioButton(selected = status == opt, onClick = null)
                            Text(
                                modifier = Modifier.padding(start = 5.dp, bottom = 4.dp),
                                text = opt,
                                fontSize = 18.sp
                            )
                        }
                    }
                    TextButton(
                        modifier = Modifier
                            .padding(top = 200.dp, bottom = 100.dp)
                            .fillMaxWidth(),
                        border = BorderStroke(1.dp, Color.Black),
                        onClick = {
                            patient.diagnosis = diagnosis
                            patient.status = statusMap[status]!!
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp),
                            text = stringResource(R.string.save_info)
                        )
                    }
                }
            }
        }
    }
}
