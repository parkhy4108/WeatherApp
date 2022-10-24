package com.dev_musashi.weather.data.repository

import com.dev_musashi.weather.data.mapper.itemsToWeatherData
import com.dev_musashi.weather.data.mapper.toWeatherInfo
import com.dev_musashi.weather.domain.Resource
import com.dev_musashi.weather.domain.repository.WeatherRepository
import com.dev_musashi.weather.data.remote.WeatherApi
import com.dev_musashi.weather.domain.data.WeatherData
import com.dev_musashi.weather.domain.data.WeatherMap
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {
    override suspend fun getWeather(
        numOfRows: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Resource<WeatherMap> {
        return try {
            Resource.Success(
                data = weatherApi.getWeather(
                    numOfRows = numOfRows,
                    baseDate = baseDate,
                    baseTime = baseTime,
                    nx = nx,
                    ny = ny
                ).response.body.itemsToWeatherData()
            )
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "error")
        }
    }

    override suspend fun getCurrentWeather(
        numOfRows: Int,
        baseDate: String,
        baseTime: String,
        nx: Int,
        ny: Int
    ): Resource<WeatherData> {
        return try {
            Resource.Success(
                data = weatherApi.getCurrentWeather(
                    numOfRows = numOfRows,
                    baseDate = baseDate,
                    baseTime = baseTime,
                    nx = nx,
                    ny = ny
                ).response.body.items.toWeatherInfo()
            )
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "error")
        }
    }
}

