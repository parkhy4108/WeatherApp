package com.dev_musashi.weather.domain.repository

import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.domain.data.WeatherMap

interface WeatherRepository {
    suspend fun getWeather(
        numOfRows: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Resource<WeatherMap>

    suspend fun getCurrentWeather(
        numOfRows: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Resource<WeatherData>
}