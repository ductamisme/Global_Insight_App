package com.aicontent.globalInsightApp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyAppNavHost(
    modifier:
    Modifier = Modifier,
    startDestination: String = NavigationItem.Main.route,
    viewModel: MainViewModel,
    viewModelSearch: SearchViewModel,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        // navController must live in all screen or your app will be crash
        composable(route = NavigationItem.Main.route) {
            MainScreen(
                navController = navController,
                listCountryState = viewModel.listCountry.value,
                restartFetchCountry = viewModel::restartFetchCountry,
                fetchCountry = viewModel::fetchCountry,
                setSelectedCountry = viewModel::setSelectedCountry,
                fetchCountryByRegion = viewModel::fetchCountryByRegion
                )
        }
        composable(route = NavigationItem.Search.route) {
            SearchScreen(
                navController = navController,
                searchText = viewModelSearch.textSearch,
                country = viewModelSearch.country.value,
                fetchCountryByName = viewModelSearch::fetchCountryByName, // tham chieu truc tiep den function trong view model
                setSelectedCountry = viewModel::setSelectedCountry,
                onSearchTextChanged = viewModelSearch::onSearchTextChanged
            )
        }
        composable(route = NavigationItem.ParticularCountry.route) {
            // Observe the selectedCountry from the ViewModel
            val selectedCountry = viewModel.selectedCountry.collectAsState().value

            // Check if the selectedCountry is not null before navigating
            if (selectedCountry != null) {
                ParticularCountry(
                    navController = navController,
                    country = selectedCountry
                )
            } else {
                // Handle the case when the selectedCountry is null
                // You can navigate to another destination or show an error message
            }
        }

        composable(route = NavigationItem.GoogleMap.route){
            val selectedCountry = viewModel.selectedCountry.collectAsState().value

            if (selectedCountry != null) {
                selectedCountry.latlng?.let { it1 -> MapsContent(latLng = it1) }
            }
        }

        composable(route = NavigationItem.FullGoogleMap.route){
            MapScreen()
        }
    }
}


enum class Screen {
    MAIN,
    SEARCH,
    PARTICULAR,
    GOOGLEMAP,
    FULLGOOGLEMAP,
}

sealed class NavigationItem(val route: String) {
    object Main : NavigationItem(Screen.MAIN.name)
    object Search : NavigationItem(Screen.SEARCH.name)
    object ParticularCountry: NavigationItem(Screen.PARTICULAR.name)
    object GoogleMap: NavigationItem(Screen.GOOGLEMAP.name)
    object FullGoogleMap: NavigationItem(Screen.FULLGOOGLEMAP.name)

}

