package com.example.dashboard.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dashboard.repo.ManifestRepository
import com.example.dashboard.ui.components.DashboardUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: ManifestRepository
) : ViewModel() {

    private val _dialogState = MutableStateFlow(
        DialogState(showStart = false, showDraftWarning = false)
    )

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getActiveManifest(),
        _dialogState
    ) { manifest, dialogs ->
        DashboardUiState(
            activeManifest = manifest,
            draftCount = manifest?.parcels?.count { it.isDraft } ?: 0,
            totalParcelCount = manifest?.parcels?.size ?: 0,
            showStartSessionDialog = dialogs.showStart,
            showDraftWarningDialog = dialogs.showDraftWarning,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState(isLoading = true)
    )

    fun onOpenStartSessionDialog() {
        _dialogState.update { it.copy(showStart = true) }
    }

    fun onDismissStartSessionDialog() {
        _dialogState.update { it.copy(showStart = false) }
    }

    fun startSession(plate: String, name: String, phone: String) {
        viewModelScope.launch {
            repository.startNewSession(plate, name, phone)
            _dialogState.update { it.copy(showStart = false) }
        }
    }

    fun onRequestCompleteSession() {
        val current = uiState.value
        val activeSessionId = current.activeManifest?.session?.id ?: return

        if (current.draftCount > 0) {
            _dialogState.update { it.copy(showDraftWarning = true) }
        } else {
            forceCompleteSession(activeSessionId)
        }
    }

    fun onDismissDraftWarningDialog() {
        _dialogState.update { it.copy(showDraftWarning = false) }
    }

    fun forceCompleteSession(sessionId: Long) {
        viewModelScope.launch {
            repository.completeSession(sessionId)
            _dialogState.update { it.copy(showDraftWarning = false) }
        }
    }

    private data class DialogState(
        val showStart: Boolean,
        val showDraftWarning: Boolean
    )
}