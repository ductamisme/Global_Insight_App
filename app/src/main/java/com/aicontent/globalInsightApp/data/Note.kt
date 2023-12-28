package com.aicontent.globalInsightApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aicontent.globalInsightApp.entity.modelAll.CapitalInfo
import com.aicontent.globalInsightApp.entity.modelAll.Car
import com.aicontent.globalInsightApp.entity.modelAll.CoatOfArms
import com.aicontent.globalInsightApp.entity.modelAll.Currencies
import com.aicontent.globalInsightApp.entity.modelAll.Demonyms
import com.aicontent.globalInsightApp.entity.modelAll.Flags
import com.aicontent.globalInsightApp.entity.modelAll.Gini
import com.aicontent.globalInsightApp.entity.modelAll.Idd
import com.aicontent.globalInsightApp.entity.modelAll.Languages
import com.aicontent.globalInsightApp.entity.modelAll.Maps
import com.aicontent.globalInsightApp.entity.modelAll.Name
import com.aicontent.globalInsightApp.entity.modelAll.PostalCode
import com.aicontent.globalInsightApp.entity.modelAll.Translations

@Entity(tableName = "note_table")
data class Country(
    val altSpellings: List<String>,
    val area: Double,
    val borders: List<String>,
    val capital: List<String>,
    val capitalInfo: CapitalInfo,
    val car: Car,
    val cca2: String,
    val cca3: String,
    val ccn3: String,
    val cioc: String,
    val coatOfArms: CoatOfArms,
    val continents: List<String>,
    val currencies: Currencies,
    val demonyms: Demonyms,
    val fifa: String,
    val flag: String,
    val flags: Flags,
    val gini: Gini,
    val idd: Idd,
    val independent: Boolean,
    val landlocked: Boolean,
    val languages: Languages,
    val latlng:List<Double>,
    val maps: Maps,
    val name: Name,
    val population: Int,
    val postalCode: PostalCode,
    val region: String,
    val startOfWeek: String,
    val status: String,
    val subregion: String,
    val timezones: List<String>,
    val tld: List<String>,
    val translations: Translations,
    val unMember: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)