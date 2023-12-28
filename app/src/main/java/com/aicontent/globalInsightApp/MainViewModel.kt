package com.aicontent.globalInsightApp

import android.graphics.Region
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aicontent.globalInsightApp.data.ApiService
import com.aicontent.globalInsightApp.entity.modelAll.entity
import com.aicontent.globalInsightApp.entity.modelAll.entityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiState<out T> {
    object Loading : ApiState<Nothing>()
    data class Success<entity>(val data: entity) : ApiState<entity>()
    data class Error(val message: String) : ApiState<Nothing>()
}

sealed class ApiStateRegion<out T> {
    object Loading : ApiStateRegion<Nothing>()
    data class Success<String>(val data: List<String>) : ApiStateRegion<List<String>>()
    data class Error(val message: String) : ApiStateRegion<Nothing>()
}

@HiltViewModel
class MainViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _listCountry = mutableStateOf<ApiState<entity>>(ApiState.Loading)
    val listCountry: State<ApiState<entity>> = _listCountry

    private val _listCountryByRegion = mutableStateOf<ApiStateRegion<List<String>>>(ApiStateRegion.Loading)
    val listCountryByRegion: State<ApiStateRegion<List<String>>> = _listCountryByRegion

    private val _selectedCountry = MutableStateFlow<entityItem?>(null)
    val selectedCountry: StateFlow<entityItem?> get() = _selectedCountry

    fun setSelectedCountry(country: entityItem?) {
        _selectedCountry.value = country
    }

    fun fetchCountry() {
        viewModelScope.launch {
            try {
                val response = apiService.getAllCountry()
                _listCountry.value = ApiState.Success(response)
            } catch (e: Exception) {
                _listCountry.value = ApiState.Error("Failed to fetch data")
            }
        }
    }

     fun fetchCountryByRegion() {
        viewModelScope.launch {
            try {
                val response = apiService.getCountryByRegion(field = "region")
                _listCountryByRegion.value = ApiStateRegion.Success(response)
            } catch (e: Exception) {
                _listCountryByRegion.value = ApiStateRegion.Error("Failed to fetch data")
            }
        }
    }

    fun restartFetchCountry() {
        _listCountry.value = ApiState.Loading
        fetchCountryByRegion()
        fetchCountry()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}
