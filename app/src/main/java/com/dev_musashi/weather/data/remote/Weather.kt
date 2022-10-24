package com.dev_musashi.weather.data.remote

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("response")
    val response : Response
)

data class Response(
    @SerializedName("header")
    val header: Header,
    @SerializedName("body")
    val body: Body
)

data class Header(
    @SerializedName("resultCode")
    val resultCode: Int,
    @SerializedName("resultMsg")
    val resultMsg: String
)

data class Body(
    @SerializedName("dataType")
    val dataType: String,
    @SerializedName("items")
    val items: Items
)

data class Items(
    @SerializedName("item")
    val item: List<Item>
)

data class Item(
    @SerializedName("baseDate")
    val baseDate: String,
    @SerializedName("baseTime")
    val baseTime: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("fcstDate")
    val fcstDate: String,
    @SerializedName("fcstTime")
    val fcstTime: String,
    @SerializedName("fcstValue")
    val fcstValue: String?,
    @SerializedName("nx")
    val nx: Int,
    @SerializedName("ny")
    val ny: Int
)