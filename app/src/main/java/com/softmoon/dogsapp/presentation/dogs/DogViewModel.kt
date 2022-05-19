package com.softmoon.dogsapp.presentation.dogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softmoon.dogsapp.domain.interators.GetDogsUseCase
import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val getDogsUseCase: GetDogsUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    fun getDogs() = viewModelScope.launch {
        _viewState.value = ViewState.Loading
        getDogsUseCase().collect {
            _viewState.value = when (it) {
                is Resource.Failure ->
                    ViewState.Failure(it)
                is Resource.Success ->
                    ViewState.Success(it.value)
            }

        }
    }

    sealed class ViewState {
        class Success(
            val dogs: List<Dog>
        ): ViewState()
        class Failure(val res: Resource.Failure): ViewState()
        object Loading: ViewState()

        val isFailureOrEmpty = this is Failure || !(this as? Success)?.dogs.isNullOrEmpty()
    }
}