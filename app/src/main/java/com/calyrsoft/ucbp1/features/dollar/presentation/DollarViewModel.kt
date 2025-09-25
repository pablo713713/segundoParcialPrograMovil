package com.calyrsoft.ucbp1.features.dollar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DollarViewModel(
    val fetchDollarUseCase: FetchDollarUseCase,
    private val localDataSource: DollarLocalDataSource
): ViewModel() {

    sealed class DollarUIState {
        object Loading : DollarUIState()
        class Error(val message: String) : DollarUIState()
        class Success(val data: DollarModel) : DollarUIState()
    }

    init {
        getDollar()
    }

    private val _uiState = MutableStateFlow<DollarUIState>(DollarUIState.Loading)
    val uiState: StateFlow<DollarUIState> = _uiState.asStateFlow()

    val history: StateFlow<List<DollarModel>> =
        localDataSource.observeHistory()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun getDollar() {
        viewModelScope.launch(Dispatchers.IO) {
                    getToken()
                    fetchDollarUseCase.invoke().collect {
                        data -> _uiState.value = DollarUIState.Success(data) }
        }
    }

    fun formatDate(millis: Long): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return fmt.format(Date(millis))
    }

    fun deleteDollar(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        localDataSource.deleteById(id)
    }


    suspend fun getToken(): String = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FIREBASE", "getInstanceId failed", task.exception)
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                    return@addOnCompleteListener
                }
                // Si la tarea fue exitosa, se obtiene el token
                val token = task.result
                Log.d("FIREBASE", "FCM Token: $token")

                // Reanudar la ejecución con el token
                continuation.resume(token ?: "")
            }
    }
}