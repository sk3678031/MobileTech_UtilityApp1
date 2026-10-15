package au.edu.jcu.assessment.utilityapp.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("current") val current: CurrentWeather
)

data class CurrentWeather(
    @SerializedName("time") val time: String,
    @SerializedName("temperature_2m") val temperature2m: Double,
    @SerializedName("relative_humidity_2m") val relativeHumidity2m: Int,
    @SerializedName("apparent_temperature") val apparentTemperature: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("wind_speed_10m") val windSpeed10m: Double
)

data class Quote(
    val content: String,
    val author: String
)
