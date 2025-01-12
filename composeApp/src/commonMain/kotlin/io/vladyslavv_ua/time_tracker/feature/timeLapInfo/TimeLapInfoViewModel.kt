package io.vladyslavv_ua.time_tracker.feature.timeLapInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.vladyslavv_ua.time_tracker.globalInterface.IViewModelMVI
import io.vladyslavv_ua.time_tracker.repo.TimeLapRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimeLapInfoViewModel(private val id: Long, private val timeLapRepo: TimeLapRepo) : ViewModel(),
    IViewModelMVI<TimeLapInfoIntent> {
    private val _state: MutableStateFlow<TimeLapInfoState> = MutableStateFlow(TimeLapInfoState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val data = timeLapRepo.getTimeLapById(id)
            if (data != null) {
                _state.value = _state.value.copy(text = data.label)
            }
        }
    }

    override fun onIntent(intent: TimeLapInfoIntent) {
        when (intent) {
            is TimeLapInfoIntent.UpdateText -> {
                updateText(intent.text)
            }
            is TimeLapInfoIntent.SaveChanges -> {
                saveChanges()
            }
        }
    }

    private fun updateText(text: String) {
        _state.value = _state.value.copy(text = text)
    }

    private fun saveChanges() {
        viewModelScope.launch(Dispatchers.Default) {
            timeLapRepo.updateLabel(id, _state.value.text)
            _state.value = _state.value.copy(updated = true)
        }
    }
}