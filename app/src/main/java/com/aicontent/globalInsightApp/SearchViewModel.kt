package com.aicontent.globalInsightApp

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aicontent.globalInsightApp.data.ApiService
import com.aicontent.globalInsightApp.entity.modelAll.entity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ApiStateSearch<out T> {
    object Loading : ApiStateSearch<Nothing>()
    data class Success<entity>(val data: entity) : ApiStateSearch<entity>()
    data class Error(val message: String) : ApiStateSearch<Nothing>()
    data class Warning(val message: String) : ApiStateSearch<Nothing>()
}

@HiltViewModel
class SearchViewModel @Inject constructor(private val apiService: ApiService): ViewModel() {
    private val _country = mutableStateOf<ApiStateSearch<entity>>(ApiStateSearch.Loading)
    val country: State<ApiStateSearch<entity>> = _country

    var textSearch by mutableStateOf("")
        private set

    fun onSearchTextChanged(newText: String) {
        textSearch = newText
    }

    init {
        fetchCountryByName()
    }

    fun fetchCountryByName() {
        viewModelScope.launch {
            try {
                if (textSearch == "" || textSearch.isEmpty() || textSearch.isBlank()){
                    _country.value = ApiStateSearch.Warning("please fill the search name!")
                } else {
                    val response = apiService.getCountryByName(textSearch)
                    _country.value = ApiStateSearch.Success(response)
                }
            } catch (e: Exception) {
                _country.value = ApiStateSearch.Error("We can not find your country (' '/) ")
            }
        }
    }
}