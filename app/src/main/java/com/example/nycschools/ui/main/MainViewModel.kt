package com.example.nycschools.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nycschools.model.School

class MainViewModel() : ViewModel() {
    private val _schoolList = MutableLiveData<List<School>>()
    val schoolList: LiveData<List<School>> = _schoolList
}