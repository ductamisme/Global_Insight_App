package com.aicontent.globalInsightApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aicontent.globalInsightApp.entity.modelAll.entity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopAppBar(
    navController: NavController,
    listCountryState : ApiState<entity>,
    restartFetchCountry: () -> Unit
) {
    val isLoading = listCountryState is ApiState.Loading // create an instance about ApiState.Loading to use "is" parameter
    TopAppBar(
        actions = {
            // Search icon
            Row {
                IconButton(
                    onClick = {
                        navController.navigate("search")
                    },
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(
                    onClick = {
                        // Check if not loading before triggering the refresh
                        if (!isLoading) {
//                            viewModel.restartFetchCountry()
                            restartFetchCountry()
                        }
                    },
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        },
        title = { Text(text = "Global Insight App") },
        navigationIcon = {
            // Refresh icon
            IconButton(
                onClick = {
                },
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenTopAppBar(navController: NavController) {
    TopAppBar(
        navigationIcon = {
            // Refresh icon
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
            }
        },
        title = { Text(text = "Global Insight App") },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ParticularCountryTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Country Details",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(NavigationItem.GoogleMap.route)
            }) {
                // Add the Google Maps icon here
//                Image(
//                    painter = painterResource(id = R.drawable.ic_google_maps), // Replace with your actual resource ID
//                    contentDescription = "Google Maps"
//                )
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "google map")
            }
        }
    )
}



