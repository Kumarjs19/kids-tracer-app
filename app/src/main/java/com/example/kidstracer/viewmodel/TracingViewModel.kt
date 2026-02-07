package com.example.kidstracer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidstracer.data.models.Letter
import com.example.kidstracer.data.models.Number
import com.example.kidstracer.data.models.UserProgress
import kotlinx.coroutines.launch

class TracingViewModel : ViewModel() {
    
    private val _currentLetter = MutableLiveData<Letter>()
    val currentLetter: LiveData<Letter> = _currentLetter
    
    private val _currentNumber = MutableLiveData<Number>()
    val currentNumber: LiveData<Number> = _currentNumber
    
    private val _userProgress = MutableLiveData<UserProgress>()
    val userProgress: LiveData<UserProgress> = _userProgress
    
    private val _allLetters = MutableLiveData<List<Letter>>()
    val allLetters: LiveData<List<Letter>> = _allLetters
    
    private val _allNumbers = MutableLiveData<List<Number>>()
    val allNumbers: LiveData<List<Number>> = _allNumbers
    
    private val _tracingAccuracy = MutableLiveData<Float>()
    val tracingAccuracy: LiveData<Float> = _tracingAccuracy
    
    private val _isCompleted = MutableLiveData<Boolean>()
    val isCompleted: LiveData<Boolean> = _isCompleted
    
    init {
        loadSampleLetters()
        loadSampleNumbers()
    }
    
    private fun loadSampleLetters() {
        val letters = listOf(
            Letter(1, "A", true, "", "", 1),
            Letter(2, "B", true, "", "", 1),
            Letter(3, "C", true, "", "", 1),
            Letter(4, "D", true, "", "", 1),
            Letter(5, "E", true, "", "", 1)
        )
        _allLetters.value = letters
    }
    
    private fun loadSampleNumbers() {
        val numbers = listOf(
            Number(1, "0", "", "", 1),
            Number(2, "1", "", "", 1),
            Number(3, "2", "", "", 1),
            Number(4, "3", "", "", 1),
            Number(5, "4", "", "", 1)
        )
        _allNumbers.value = numbers
    }
    
    fun selectLetter(letter: Letter) {
        _currentLetter.value = letter
        _isCompleted.value = false
        _tracingAccuracy.value = 0f
    }
    
    fun selectNumber(number: Number) {
        _currentNumber.value = number
        _isCompleted.value = false
        _tracingAccuracy.value = 0f
    }
    
    fun updateTracingAccuracy(accuracy: Float) {
        _tracingAccuracy.value = accuracy
        if (accuracy >= 70f) {
            _isCompleted.value = true
        }
    }
    
    fun saveProgress(letterId: Int, type: String, accuracy: Float) {
        viewModelScope.launch {
            val progress = UserProgress(
                letterOrNumberId = letterId,
                type = type,
                isCompleted = accuracy >= 70f,
                accuracy = accuracy,
                attempts = 1,
                dateCompleted = System.currentTimeMillis(),
                stars = when {
                    accuracy >= 90f -> 3
                    accuracy >= 80f -> 2
                    accuracy >= 70f -> 1
                    else -> 0
                }
            )
            _userProgress.value = progress
        }
    }
    
    fun resetTracing() {
        _isCompleted.value = false
        _tracingAccuracy.value = 0f
    }
}