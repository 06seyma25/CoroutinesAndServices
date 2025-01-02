package com.example.jetpackcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.ui.views.convertUnixToTime

@Composable
fun WeatherCard(forecastItem: ForecastItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color(0xFFBBDEFB), shape = RoundedCornerShape(16.dp)) // Blue background
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)), // Rounded corners
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image for the weather icon
        Image(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${forecastItem.weather.firstOrNull()?.icon}@2x.png"),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop // Crop to fit the square size
        )

        Spacer(modifier = Modifier.width(24.dp)) // Space between the icon and text

        // Column to arrange text vertically
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Date/Time displayed in a readable format
            Text(
                text = convertUnixToTime(forecastItem.dt), // Convert Unix timestamp to readable time
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 4.dp) // Space below the time text
            )
            // Temperature and weather description displayed below the time
            Text(
                text = "${forecastItem.main.temp}°C - ${forecastItem.weather.firstOrNull()?.description ?: "N/A"}",
                color = Color.Gray, // Color for temperature and description
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
            )
        }
    }
}
