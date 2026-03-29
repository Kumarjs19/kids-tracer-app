package com.example.kidstracer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kidstracer.data.models.Letter
import com.example.kidstracer.data.models.Number
import com.example.kidstracer.data.models.TracingType
import com.example.kidstracer.data.models.UserProgress

class TracingViewModel : ViewModel() {

    companion object {
        private const val ACCURACY_THRESHOLD = 70f
        private const val STARS_THREE_THRESHOLD = 90f
        private const val STARS_TWO_THRESHOLD = 80f
    }

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
        val letters = ('A'..'Z').mapIndexed { index, char ->
            Letter(
                id = index + 1,
                character = char.toString(),
                uppercase = true,
                difficulty = when (index) {
                    in 0..8 -> 1
                    in 9..17 -> 2
                    else -> 3
                }
            )
        }
        _allLetters.value = letters
    }

    private fun loadSampleNumbers() {
        val numbers = (0..9).map { digit ->
            Number(id = digit + 1, digit = digit.toString(), difficulty = 1)
        }
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
        if (accuracy >= ACCURACY_THRESHOLD) {
            _isCompleted.value = true
        }
    }

    fun saveProgress(itemId: Int, type: TracingType, accuracy: Float) {
        val progress = UserProgress(
            letterOrNumberId = itemId,
            type = type,
            isCompleted = accuracy >= ACCURACY_THRESHOLD,
            accuracy = accuracy,
            attempts = 1,
            dateCompleted = System.currentTimeMillis(),
            stars = starsForAccuracy(accuracy)
        )
        _userProgress.value = progress
    }

    fun resetTracing() {
        _isCompleted.value = false
        _tracingAccuracy.value = 0f
    }

    private fun starsForAccuracy(accuracy: Float): Int = when {
        accuracy >= STARS_THREE_THRESHOLD -> 3
        accuracy >= STARS_TWO_THRESHOLD -> 2
        accuracy >= ACCURACY_THRESHOLD -> 1
        else -> 0
    }
}
