package com.aicontent.globalInsightApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.aicontent.globalInsightApp.entity.modelAll.entity
import com.aicontent.globalInsightApp.entity.modelAll.entityItem
import com.aicontent.globalInsightApp.ui.theme.MyApplicationTheme
//import com.squareup.sqldelight.db.SqlDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.NumberFormat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val viewModelSearch: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavHost(
                        navController = rememberNavController(),
                        viewModel = viewModel,
                        viewModelSearch = viewModelSearch
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    listCountryState: ApiState<entity>,
//    listCountryByRegion: ApiStateRegion<List<String>>,
    restartFetchCountry: () -> Unit,
    setSelectedCountry: (entityItem) -> Unit,
    fetchCountry: () -> Unit,
    fetchCountryByRegion: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val paperState = rememberPagerState(pageCount = { HomeTabs.values().size })
    val selectedTabIndex = remember { derivedStateOf { paperState.currentPage } }

    // Modifier.then để làm điều kiện
    LaunchedEffect(Unit) {
        fetchCountry()
        fetchCountryByRegion()
    }

    Scaffold(
        topBar = {
            MainScreenTopAppBar(
                navController = navController,
                listCountryState = listCountryState,
                restartFetchCountry = restartFetchCountry
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                HomeTabs.values().forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.outline,
                        onClick = {
                            scope.launch {
                                paperState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { Text(text = currentTab.text) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTabIndex.value == index) currentTab.selectedIcon else currentTab.unselectedIcon,
                                contentDescription = "Tab icon"
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = paperState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    when (selectedTabIndex.value) {
                        0 -> AllCountry(
                            navController = navController,
                            listCountryState = listCountryState,
                            setSelectedCountry = setSelectedCountry
                        )

                        1 -> AllCountryFlags(
                            navController = navController,
                            listCountryState = listCountryState,
                            setSelectedCountry = setSelectedCountry
                        )

                        else ->
//                            MapScreen()
                            when (listCountryState) {
                                ApiState.Loading -> {
                                    // Show loading indicator
                                    CircularProgressIndicator(
                                        modifier = Modifier
//                                            .align(Alignment.CenterHorizontally)
                                    )
                                }

                                is ApiState.Success -> {
                                    // Display the fetched data using Jetpack Compose components
                                    LazyColumn {
                                        items(
                                            items = listCountryState.data
                                                .sortedBy { it.region }
                                                .distinctBy { it.region },
//                                            key = null
                                        ) { country ->
                                            when (country.region) {
                                                "Africa" -> Text(text = country.flag.toString())
                                                "Americas" -> Text(text = country.flag.toString())
                                                "Antarctic" -> Text(text = country.flag.toString())
                                                "Asia" -> Text(text = country.flag.toString())
                                                "Europe" -> Text(text = country.flag.toString())
                                                "Oceania" -> Text(text = country.flag.toString())
                                            }
                                        }
                                    }
                                }

                                is ApiState.Error -> {
                                    // Show error message
                                    Text(
                                        text = "Failed to fetch data: ${listCountryState.message}",
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
//                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun AllCountry(
    navController: NavController,
    listCountryState: ApiState<entity>,
    setSelectedCountry: (entityItem) -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        when (listCountryState) {
            ApiState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            is ApiState.Success -> {
                // Display the fetched data using Jetpack Compose components
                LazyColumn {
                    items(
                        items = listCountryState.data.sortedBy { it.name?.common },
//                        key = null
                    ) { country ->
                        CountryCard(
                            country,
                            Modifier,
                            onClick = {
                                setSelectedCountry(country)
                                navController.navigate(NavigationItem.ParticularCountry.route)
                            }
                        )
                    }
                }
            }

            is ApiState.Error -> {
                // Show error message
                Text(
                    text = "Failed to fetch data: ${listCountryState.message}",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun AllCountryFlags(
    navController: NavController,
    listCountryState: ApiState<entity>,
    setSelectedCountry: (entityItem) -> Unit
) {
    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
        when (listCountryState) {
            ApiState.Loading -> {
                // Show loading indicator
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            is ApiState.Success -> {
                // Display the fetched data using Jetpack Compose components
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
//                    contentPadding = PaddingValues(8.dp),
                    content = {
                        items(
                            items = listCountryState.data.sortedBy { it.name?.common },
//                            key = null
                        ) { country ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(MaterialTheme.colorScheme.surface) // Use surface color for the background
                                    .clip(MaterialTheme.shapes.medium) // Apply rounded corners
                                    .clickable {
                                        setSelectedCountry(country)
                                        navController.navigate(NavigationItem.ParticularCountry.route)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    AsyncImage(
                                        model = country.flags?.png,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .clip(MaterialTheme.shapes.medium),
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Country: ${country.name?.common}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                )
            }

            is ApiState.Error -> {
                // Show error message
                Text(
                    text = "Failed to fetch data: ${listCountryState.message}",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun CountryCard(country: entityItem, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(MaterialTheme.colorScheme.surface) // Use surface color for the background
            .clip(MaterialTheme.shapes.medium) // Apply rounded corners
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Country: ${country.name?.common}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Capital: ${country.capital}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Population: ${country.population?.formatNumber()}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Region: ${country.region}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Subregion: ${country.subregion}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "flag: ${country.flag}",
                fontSize = 16.sp,
                modifier = Modifier// Apply rounded corners to the image
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}


private fun Int.formatNumber(): String {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(this)
}

enum class HomeTabs(
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val text: String
) {
    Main(
        unselectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Outlined.Home,
        text = "Home"
    ),
    Second(
        unselectedIcon = Icons.Outlined.Favorite,
        selectedIcon = Icons.Outlined.Favorite,
        text = "Favourite"
    ),
    Third(
        unselectedIcon = Icons.Outlined.LocationOn,
        selectedIcon = Icons.Outlined.LocationOn,
        text = "LocationOn"
    )
}


//val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "test.db")
//val database = Database(driver)
//
//val playerQueries: PlayerQueries = database.version
//
//println(playerQueries.selectAll().executeAsList())
//// Prints [HockeyPlayer(15, "Ryan Getzlaf")]
//
//playerQueries.insert(player_number = 10, full_name = "Corey Perry")
//println(playerQueries.selectAll().executeAsList())
//// Prints [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]
//
//val player = HockeyPlayer(10, "Ronald McDonald")
//playerQueries.insertFullPlayerObject(player)