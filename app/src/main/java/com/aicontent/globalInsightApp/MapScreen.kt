package com.aicontent.globalInsightApp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen() {
    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.SATELLITE
            )
        )
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings
        )
//        Switch(
//            checked = uiSettings.zoomControlsEnabled,
//            onCheckedChange = {
//                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
//            }
//        )
    }

}

@Composable
fun MapsContent(latLng: List<Double>) {
    val locate = LatLng(latLng.first(), latLng.last())
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locate, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = locate),
            title = "Singapore",
            snippet = "Marker in Singapore"
        ) // đánh dấu

        Circle(
            center = locate,
            clickable = false,
            fillColor = Color.Red.copy(alpha = 0.3f),
            radius = 100.0, // the radius of the circle in meters.
            strokeColor = Color.Blue,
            strokePattern = null, // một chuỗi các PatternItem được lặp lại dọc theo đường viền của vòng tròn
            strokeWidth = 1f, // the width of the circle's outline in screen pixels
            tag = "oke",
            visible = true,
            zIndex = 0f,
            onClick = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMap(){
    val latLng = listOf(16.16666666, 107.83333333)
    MapsContent(latLng)
}