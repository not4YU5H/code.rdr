package com.coderdr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderdr.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class viewModel @Inject constructor(
    private val repo: MainRepo
): ViewModel() {

    private val _state = MutableStateFlow(ScannerScreenData())
    val state = _state.asStateFlow()

    fun startScanner() {
        viewModelScope.launch {
            repo.startScanner().collect{ data->
                if(!data.isNullOrBlank()){
                    _state.value = state.value.copy(
                        details = data
                    )
                }
            }
        }
    }
}