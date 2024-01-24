package com.aicontent.globalInsightApp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aicontent.globalInsightApp.entity.modelAll.entity
import com.aicontent.globalInsightApp.entity.modelAll.entityItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    country: ApiStateSearch<entity>,
    fetchCountryByName: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    searchText: String,
    setSelectedCountry: (entityItem) -> Unit
) {
    LaunchedEffect(searchText, Unit) {
        fetchCountryByName()
    }

    Scaffold(
        topBar = { SearchScreenTopAppBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(30.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    onSearchTextChanged(it)
                    // Implement search logic here
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                singleLine = true,
                label = {
                    Text(text = "Search")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    autoCorrect = true
                    )
            )

            // Display search results
            when (country) {
                is ApiStateSearch.Warning -> {
                    Text(text = country.message, color = Color.LightGray)
                }

                ApiStateSearch.Loading -> {
                    // Display loading indicator
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is ApiStateSearch.Success -> {
                    // Display search results
                    LazyColumn {
                        items(country.data) { country ->
                            CountryCard(
                                country,
                                Modifier,
                                onClick = {
                                    setSelectedCountry(country)
                                    navController.navigate(NavigationItem.ParticularCountry.route)
                                })
                        }
                    }
                }

                is ApiStateSearch.Error -> {
                    // Display error message
                    Text(text = country.message, color = Color.Red)
                }
            }
        }
    }
}