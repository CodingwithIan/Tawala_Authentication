package com.example.dashboard.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

private val DarkSurface = Color(0xFF1E1E1E)
private val InputBackground = Color(0xFF2A2A2A)
private val SecondaryText = Color(0xFF9E9E9E)

@Composable
fun StartSessionDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (plate: String, conductorName: String, conductorPhone: String) -> Unit
) {
    var vehiclePlate by rememberSaveable { mutableStateOf("") }
    var conductorName by rememberSaveable { mutableStateOf("") }
    var conductorPhone by rememberSaveable { mutableStateOf("") }

    val isFormValid = vehiclePlate.isNotBlank() && conductorName.isNotBlank() && conductorPhone.isNotBlank()

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = DarkSurface,
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Start Vehicle Session",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Enter vehicle and conductor details to begin accepting parcels.",
                    color = SecondaryText,
                    fontSize = 13.sp
                )

                OutlinedTextField(
                    value = vehiclePlate,
                    onValueChange = { vehiclePlate = it.uppercase() },
                    label = { Text("Vehicle Plate (e.g., KDS 243Y)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                    colors = customTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = conductorName,
                    onValueChange = { conductorName = it },
                    label = { Text("Conductor Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    colors = customTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = conductorPhone,
                    onValueChange = { conductorPhone = it },
                    label = { Text("Conductor Phone Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = customTextFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.textButtonColors(contentColor = SecondaryText)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (isFormValid) {
                                onConfirm(vehiclePlate, conductorName, conductorPhone)
                            }
                        },
                        enabled = isFormValid,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContainerColor = Color(0xFF333333),
                            disabledContentColor = Color(0xFF666666)
                        )
                    ) {
                        Text("Start Session", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = InputBackground,
    unfocusedContainerColor = InputBackground,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color(0xFF333333),
    focusedLabelColor = Color.White,
    unfocusedLabelColor = SecondaryText,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White
)