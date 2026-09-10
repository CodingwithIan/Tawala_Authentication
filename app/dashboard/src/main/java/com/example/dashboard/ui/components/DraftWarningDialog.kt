package com.example.dashboard.ui.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkSurface = Color(0xFF1E1E1E)
private val SecondaryText = Color(0xFF9E9E9E)

@Composable
fun DraftWarningDialog(
    draftCount: Int,
    onDismissRequest: () -> Unit,
    onConfirmComplete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = DarkSurface,
        title = {
            Text(
                text = "Unsaved Draft Parcels",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(
                text = "You have $draftCount uncommitted draft parcel(s) in this session. Completing the manifest will close the shift. Are you sure you want to proceed?",
                color = SecondaryText,
                fontSize = 14.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmComplete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                )
            ) {
                Text("Complete Shift")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(contentColor = SecondaryText)
            ) {
                Text("Cancel")
            }
        }
    )
}