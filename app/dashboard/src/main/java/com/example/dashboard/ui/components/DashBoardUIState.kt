package com.example.dashboard.ui.components

import com.example.dashboard.utilis.ManifestWithParcels


data class DashboardUiState(
    val activeManifest: ManifestWithParcels? = null,
    val draftCount: Int = 0,
    val totalParcelCount: Int = 0,
    val showStartSessionDialog: Boolean = false,
    val showDraftWarningDialog: Boolean = false,
    val isLoading: Boolean = false
)