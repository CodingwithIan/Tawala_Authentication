package com.example.dashboard.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dashboard.viewmodel.DashboardViewModel


private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val SecondaryText = Color(0xFF9E9E9E)

@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel,
    onNavigateToAddParcel: (sessionId: Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    DashboardContent(
        uiState = state,
        onStartSessionClick = viewModel::onOpenStartSessionDialog,
        onCompleteSessionClick = viewModel::onRequestCompleteSession,
        onAddParcelClick = {
            state.activeManifest?.session?.id?.let(onNavigateToAddParcel)
        }
    )

    if (state.showStartSessionDialog) {
        StartSessionDialog(
            onDismissRequest = viewModel::onDismissStartSessionDialog,
            onConfirm = viewModel::startSession
        )
    }

    if (state.showDraftWarningDialog) {
        DraftWarningDialog(
            draftCount = state.draftCount,
            onDismissRequest = viewModel::onDismissDraftWarningDialog,
            onConfirmComplete = {
                state.activeManifest?.session?.id?.let(viewModel::forceCompleteSession)
            }
        )
    }
}

@Composable
fun DashboardContent(
    uiState: DashboardUiState,
    onStartSessionClick: () -> Unit,
    onCompleteSessionClick: () -> Unit,
    onAddParcelClick: () -> Unit
) {
    val hasActiveSession = uiState.activeManifest != null

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onAddParcelClick,
                    enabled = hasActiveSession,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color(0xFF242424),
                        disabledContentColor = Color(0xFF666666)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (hasActiveSession) "+ Add Parcel" else "+ Add Parcel (Start Session First)",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Manifest Section (Active vs Empty)
            if (hasActiveSession) {
                ActiveManifestCard(
                    session = uiState.activeManifest!!.session,
                    parcelCount = uiState.totalParcelCount,
                    onCompleteClick = onCompleteSessionClick
                )
            } else {
                EmptyManifestCard(onStartSessionClick = onStartSessionClick)
            }

            // Session Parcels Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SESSION PARCELS",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Drafts (${uiState.draftCount})",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }

            // Parcel List Placeholder Area
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No parcels recorded for this shift yet.",
                        color = SecondaryText,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}




