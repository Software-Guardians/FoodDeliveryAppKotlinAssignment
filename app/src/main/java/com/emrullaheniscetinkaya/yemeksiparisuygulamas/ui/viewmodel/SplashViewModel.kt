package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(var repository: YemeklerRepository) : ViewModel() {
    private val _navigasyonDurumu= MutableLiveData<Boolean>()
    val navigasyonDurumu: LiveData<Boolean> = _navigasyonDurumu
    init {
        viewModelScope.launch {
            delay(3000)
            _navigasyonDurumu.value=true
        }
    }
}