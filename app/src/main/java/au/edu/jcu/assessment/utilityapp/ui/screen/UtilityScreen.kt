package au.edu.jcu.assessment.utilityapp.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.components.WeatherDetailItem
import au.edu.jcu.assessment.utilityapp.ui.components.getWeatherDescription
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.UtilityViewModel

@Composable
fun UtilityScreen(viewModel: UtilityViewModel) {
    val weather = viewModel.weatherResponse
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val selectedCity = viewModel.selectedCity
    val isCelsius = viewModel.isCelsius
    val showQuote = viewModel.showQuote
    val quote = viewModel.currentQuote
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        // Landscape two-pane layout: side-by-side weather & quote, no scrolling, with a vertical border line
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Weather & Wellness",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Location: ${selectedCity.name}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { viewModel.loadWeather() },
                    enabled = !isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Connection Error",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Button(onClick = { viewModel.loadWeather() }) {
                            Text("Retry")
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left Pane: Weather Card
                    Box(modifier = Modifier.weight(1f)) {
                        if (weather != null) {
                            val current = weather.current
                            val tempC = current.temperature2m
                            val tempF = tempC * 9 / 5 + 32
                            val displayTemp = if (isCelsius) "${String.format("%.1f", tempC)}°C" else "${String.format("%.1f", tempF)}°F"
                            val apparentC = current.apparentTemperature
                            val apparentF = apparentC * 9 / 5 + 32
                            val displayApparent = if (isCelsius) "${String.format("%.1f", apparentC)}°C" else "${String.format("%.1f", apparentF)}°F"
                            val condition = getWeatherDescription(current.weatherCode)

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Current Conditions",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = displayTemp,
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = condition,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    HorizontalDivider()
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        WeatherDetailItem(label = "Feels Like", value = displayApparent)
                                        WeatherDetailItem(label = "Humidity", value = "${current.relativeHumidity2m}%")
                                        WeatherDetailItem(label = "Wind", value = "${current.windSpeed10m} km/h")
                                    }
                                }
                            }
                        }
                    }

                    // Clear Border Line / Vertical Divider between panels
                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .width(2.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )

                    // Right Pane: Motivation Quote Card
                    Box(modifier = Modifier.weight(1f)) {
                        if (showQuote) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Daily Mindfulness & Motivation",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = quote,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontStyle = FontStyle.Italic
                                    )
                                    OutlinedButton(
                                        onClick = { viewModel.refreshQuote() },
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Text("New Quote")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        // Portrait Layout: weighted cards to fill vertical space perfectly without bottom empty space
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // App Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Weather & Wellness",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Location: ${selectedCity.name}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = { viewModel.loadWeather() },
                    enabled = !isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Connection Error",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Button(onClick = { viewModel.loadWeather() }) {
                            Text("Retry")
                        }
                    }
                }
            } else if (weather != null) {
                val current = weather.current
                val tempC = current.temperature2m
                val tempF = tempC * 9 / 5 + 32
                val displayTemp = if (isCelsius) "${String.format("%.1f", tempC)}°C" else "${String.format("%.1f", tempF)}°F"
                val apparentC = current.apparentTemperature
                val apparentF = apparentC * 9 / 5 + 32
                val displayApparent = if (isCelsius) "${String.format("%.1f", apparentC)}°C" else "${String.format("%.1f", apparentF)}°F"
                val condition = getWeatherDescription(current.weatherCode)

                // Weather Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "Current Conditions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = displayTemp,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = condition,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        HorizontalDivider()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherDetailItem(label = "Feels Like", value = displayApparent)
                            WeatherDetailItem(label = "Humidity", value = "${current.relativeHumidity2m}%")
                            WeatherDetailItem(label = "Wind Speed", value = "${current.windSpeed10m} km/h")
                        }
                    }
                }
            }

            // Daily Wellness / Mindfulness Quote Card
            if (showQuote) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "Daily Mindfulness & Motivation",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = quote,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic
                        )
                        OutlinedButton(
                            onClick = { viewModel.refreshQuote() },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("New Quote")
                        }
                    }
                }
            }
        }
    }
}
