package com.example.nycschools.ui.school

import android.util.Log
import androidx.lifecycle.*
import com.example.nycschools.BuildConfig
import com.example.nycschools.api.SchoolNetwork
import com.example.nycschools.model.School
import com.example.nycschools.model.TestScores
import kotlinx.coroutines.launch

/**
 * ViewModel to hold the necessary data for the test score detail fragment
 * @param school The school object representing the item selected from the list
 */
class SchoolDetailViewModel(school: School) : ViewModel() {
    private val _testArray = MutableLiveData<List<TestScores>>()
    private val _testScores = MutableLiveData<TestScores>()
    val testScores: LiveData<TestScores> = _testScores


    init {
        viewModelScope.launch {
            _testArray.value = SchoolNetwork.schools.getTestScores(school.dbn, BuildConfig.API_KEY)
            extractTestScore(school)
        }
    }

    class Factory(val school: School) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SchoolDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return SchoolDetailViewModel(school) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    /**
     * The api returns the test scores for a single school stored inside a json array
     * I've want to extract the single object from the array in one place, rather than trying to
     * get the object from the array in multiple places
     * @param school I've passed in the school object so we can still display the school name,
     * and potentially other info, after an exception
     */
    private fun extractTestScore(school: School) {
        try {
            _testScores.value = _testArray.value?.get(0)
        } catch (e: java.lang.IndexOutOfBoundsException) {
            Log.e("SchoolDetailViewModel", e.printStackTrace().toString())
            _testScores.value = TestScores("--", school.school_name, "--", "--", "--", "--")
        }
    }
}