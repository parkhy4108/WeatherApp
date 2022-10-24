package com.dev_musashi.weather.presentation.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.weather.R.drawable as AppImg

@Composable
fun CurrentWeather(
    state: HomeState,
    modifier: Modifier = Modifier,
    locationClick: ()->Unit
) {
    state.currentWeather?.let {
        Card(
            backgroundColor = Color.White,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = AppImg.ic_my_location),
                            contentDescription = null,
                            modifier = Modifier.clickable { locationClick() }
                        )
                        state.address?.let {
                            Text(
                                text = "${state.address.adminArea} ${state.address.subLocality}",
                                modifier = Modifier,
                                color = Color.Black
                            )
                        }
                    }
                    Text(
                        text = "Today ${state.currentWeather.time.substring(0..1)} : ${state.currentWeather.time.substring(2..3)}",
                        modifier = Modifier,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = state.currentWeather.weatherClassification.iconRes),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${state.currentWeather.temperature}°C",
                    fontSize = 45.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CurrentStatus(
                        value = state.currentWeather.precipitationAmount,
                        unit = "",
                        icon = AppImg.ic_raindrops,
                        modifier = Modifier
                    )
                    CurrentStatus(
                        value = state.currentWeather.humidity,
                        unit = "%",
                        icon = AppImg.ic_humidity,
                        modifier = Modifier
                    )
                    CurrentStatus(
                        value = state.currentWeather.wind,
                        unit = "m/s",
                        icon = AppImg.ic_wind,
                        modifier = Modifier
                    )
                }
            }
        }
    }

}

@Composable
fun CurrentStatus(
    value: String,
    unit: String,
    @DrawableRes icon: Int,
    modifier: Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value + unit
        )
    }
}