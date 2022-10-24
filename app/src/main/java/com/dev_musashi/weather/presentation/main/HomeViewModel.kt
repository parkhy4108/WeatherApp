package com.dev_musashi.weather.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.weather.util.ConvertXY
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.location.LocationTracker
import com.dev_musashi.weather.domain.usecase.GetCurrentWeather
import com.dev_musashi.weather.domain.usecase.GetWeather
import com.dev_musashi.weather.util.getBaseTime
import com.dev_musashi.weather.util.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeather: GetWeather,
    private val getCurrentWeather: GetCurrentWeather,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val cal: Calendar = Calendar.getInstance()
    private val currentHour: String = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
    private val currentMinute: String = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time)
    private val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
    private val baseDate =
        if (currentHour.toInt() <= 4) {
            if (currentMinute.toInt() <= 10) {
                (currentDate.toInt() - 1).toString()
            } else {
                currentDate
            }
        } else currentDate
    private val baseTime = getTime(currentHour, currentMinute)
    private val currentBaseTime = getBaseTime(currentHour, currentMinute)
    private val currentBaseDate =
        if (currentHour == "00") (currentDate.toInt() - 1).toString() else currentDate

    var state = mutableStateOf(HomeState())
        private set

    fun loadCurrentWeather() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            locationTracker.getCurrentLocation()?.let { result ->
                val point = ConvertXY(result.first!!.latitude, result.second!!.longitude)
                val nx = point.x
                val ny = point.y

                state.value = state.value.copy(address = result.second!!)

                when (val currentWeather = getCurrentWeather(
                    numOfRows = 60,
                    baseDate = currentBaseDate,
                    baseTime = currentBaseTime,
                    nx = nx,
                    ny = ny
                )) {
                    is Resource.Success -> {
                        state.value = state.value.copy(currentWeather = currentWeather.data)
                    }
                    is Resource.Error -> {
                        state.value = state.value.copy(currentWeather = null, isLoading = false)
                    }
                }

                when (val weatherData = getWeather(
                    numOfRows = 580,
                    baseDate = baseDate,
                    baseTime = baseTime,
                    nx = nx,
                    ny = ny
                )) {
                    is Resource.Success -> {
                        state.value = state.value.copy(
                            weatherData = weatherData.data?.weatherMap,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        state.value = state.value.copy(weatherData = null, isLoading = false)
                    }
                }
            }
        }
    }


    fun locationClicked() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { result ->
                state.value = state.value.copy(address = result.second)
            }
        }
    }

}


