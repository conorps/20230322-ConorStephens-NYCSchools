package com.example.nycschools.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import com.example.nycschools.data.SchoolRepository
import com.example.nycschools.database.getDatabase
import kotlinx.coroutines.launch
import java.io.IOException

class SchoolListViewModel(application: Application) : ViewModel() {

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val schoolRepository = SchoolRepository(getDatabase(application))

    /**
     * Views should use this schoolList to get access to the data
     */
    val schoolList = schoolRepository.schools

    private var _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    /**
     * Flag to display the error message.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                schoolRepository.refreshSchools()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                if (schoolList.value.isNullOrEmpty()) _eventNetworkError.value = true
            }
        }
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /**
     * Factory for constructing SchoolListViewModel
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SchoolListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return SchoolListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}