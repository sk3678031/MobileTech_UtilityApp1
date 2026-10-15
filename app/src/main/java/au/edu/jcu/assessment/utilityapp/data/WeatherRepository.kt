package au.edu.jcu.assessment.utilityapp.data

class WeatherRepository(
    private val apiService: WeatherApiService = WeatherApiService.create()
) {
    suspend fun fetchWeather(lat: Double, lon: Double): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeatherData(lat, lon)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
