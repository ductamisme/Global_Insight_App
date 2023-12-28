package com.aicontent.globalInsightApp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aicontent.globalInsightApp.entity.modelAll.entityItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.lang.reflect.Field

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticularCountry(country: entityItem, navController: NavController) {
    Scaffold(
        topBar = {
            ParticularCountryTopAppBar(navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding())
        ) {

            Text(
                text = country.name!!.common,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            AsyncImage(
                model = country.flags!!.png,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )


            Spacer(modifier = Modifier.height(16.dp))


            val stringValueLanguages = country.languages?.javaClass?.declaredFields
                ?.mapNotNull { field ->
                    field.isAccessible = true
                    val value = field.get(country.languages)
                    if (value is String) value else null
                }

// Now stringValueLanguages contains a list of String values or an error message


            LazyColumn {
                item (
//                    key = country.name
                ){
                    EntityItemRow("Capital", country.capital?.joinToString() ?: "No Information")
                    EntityItemRow("Area", "${country.area?.toInt()} sq km")
//                    EntityItemRow("Area", if( country.area.) sq km")
                    EntityItemRow("Population", country.population.toString())
                    EntityItemRow("Region", country.region)
                    EntityItemRow("Subregion", country.subregion)
                    EntityItemRow("Continents", country.continents?.joinToString()  ?: "No Information")
                    EntityItemRow("Languages", value = "$stringValueLanguages")
                    EntityItemRow("Timezones", country.timezones?.joinToString()  ?: "No Information")
//                    EntityItemRow("Currency", "$currencyNameValues")
//                EntityItemRow("Demonyms", country.demonyms.toString())
//                EntityItemRow("Gini", country.gini.toString())
//                EntityItemRow("Idd", country.idd.toString())
                    EntityItemRow("Independent", country.independent.toString())
                    EntityItemRow("Landlocked", country.landlocked.toString())
//                EntityItemRow("Maps", country.maps.toString())
                    EntityItemRow("StartOfWeek", country.startOfWeek)
//                EntityItemRow("Status", country.status)
//                EntityItemRow("TLD", country.tld.joinToString())
//                EntityItemRow("Translations", country.translations.toString())
//                    EntityItemRow("UN Member", country.unMember.toString())
                    // Add more rows for other data
                }
            }
        }
    }
}

@Composable
fun EntityItemRow(key: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value ?: "No Information",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}
