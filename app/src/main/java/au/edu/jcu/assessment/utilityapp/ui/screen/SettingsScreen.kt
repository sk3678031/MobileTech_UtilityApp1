package au.edu.jcu.assessment.utilityapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.viewmodel.UtilityViewModel

@Composable
fun SettingsScreen(viewModel: UtilityViewModel) {
    val availableCities = viewModel.availableCities
    val selectedCity = viewModel.selectedCity
    val isCelsius = viewModel.isCelsius
    val showQuote = viewModel.showQuote

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "App Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Customize your utility app preferences below. Note that settings apply in real-time.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider()

        // City Selection
        Text(
            text = "Select Location",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        availableCities.forEach { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = city.name, style = MaterialTheme.typography.bodyLarge)
                RadioButton(
                    selected = selectedCity.name == city.name,
                    onClick = { viewModel.selectCity(city) }
                )
            }
        }

        HorizontalDivider()

        // Temperature Unit
        Text(
            text = "Temperature Unit",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Use Celsius (°C)", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isCelsius,
                onCheckedChange = { viewModel.setTemperatureUnit(it) }
            )
        }

        HorizontalDivider()

        // Show Daily Quote Toggle
        Text(
            text = "Daily Wellness Quote",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Show quote on main screen", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = showQuote,
                onCheckedChange = { viewModel.updateShowQuote(it) }
            )
        }
    }
}
