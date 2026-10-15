package au.edu.jcu.assessment.utilityapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.WeatherRepository
import au.edu.jcu.assessment.utilityapp.data.WeatherResponse
import kotlinx.coroutines.launch

data class CityOption(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

class UtilityViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    val availableCities = listOf(
        CityOption("Townsville, QLD", -19.2590, 146.8169),
        CityOption("Cairns, QLD", -16.9186, 145.7781),
        CityOption("Brisbane, QLD", -27.4698, 153.0251),
        CityOption("Sydney, NSW", -33.8688, 151.2093),
        CityOption("Melbourne, VIC", -37.8136, 144.9631)
    )

    var selectedCity by mutableStateOf(availableCities[0])
        private set

    var isCelsius by mutableStateOf(true)
        private set

    var showQuote by mutableStateOf(true)
        private set

    var weatherResponse by mutableStateOf<WeatherResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val dailyQuotes = listOf(
        "\"Mindfulness is not about getting away from the chaos, but finding peace within it.\"",
        "\"Every morning we are born again. What we do today is what matters most.\"",
        "\"Small daily progress over time leads to staggering results.\"",
        "\"Take a deep breath. You are doing better than you think.\"",
        "\"Nature does not hurry, yet everything is accomplished.\""
    )

    var currentQuote by mutableStateOf(dailyQuotes.random())
        private set

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = repository.fetchWeather(selectedCity.latitude, selectedCity.longitude)
            result.onSuccess { data ->
                weatherResponse = data
                isLoading = false
            }.onFailure { error ->
                errorMessage = error.localizedMessage ?: "Failed to fetch weather data."
                isLoading = false
            }
        }
    }

    fun selectCity(city: CityOption) {
        selectedCity = city
        loadWeather()
    }

    fun setTemperatureUnit(celsius: Boolean) {
        isCelsius = celsius
    }

    fun updateShowQuote(show: Boolean) {
        showQuote = show
    }

    fun refreshQuote() {
        currentQuote = dailyQuotes.random()
    }
}
