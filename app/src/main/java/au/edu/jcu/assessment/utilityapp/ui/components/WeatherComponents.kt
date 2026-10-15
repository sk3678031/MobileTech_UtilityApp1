package au.edu.jcu.assessment.utilityapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getWeatherDescription(code: Int): String {
    return when (code) {
        0 -> "Clear sky ☀️"
        1, 2 -> "Partly cloudy ⛅"
        3 -> "Overcast ☁️"
        45, 48 -> "Foggy 🌫️"
        51, 53, 55, 56, 57 -> "Drizzle 🌧️"
        61, 63, 65, 66, 67 -> "Rain 🌧️"
        71, 73, 75, 77 -> "Snow ❄️"
        80, 81, 82 -> "Rain showers 🌦️"
        95, 96, 99 -> "Thunderstorm ⚡"
        else -> "Fair weather 🌤️"
    }
}
