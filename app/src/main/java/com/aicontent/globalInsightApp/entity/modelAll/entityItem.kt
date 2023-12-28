package com.aicontent.globalInsightApp.entity.modelAll

data class entityItem(
    val altSpellings: List<String>? = null,
    val area: Double? = null,
    val borders: List<String>? = null,
    val capital: List<String>? = null,
    val capitalInfo: CapitalInfo? = null,
    val car: Car? = null,
    val cca2: String? = null,
    val cca3: String? = null,
    val ccn3: String? = null,
    val cioc: String? = null,
    val coatOfArms: CoatOfArms? = null,
    val continents: List<String>? = null,
    val currencies: Currencies? = null,
    val demonyms: Demonyms? = null,
    val fifa: String? = null,
    val flag: String? = null,
    val flags: Flags? = null,
    val gini: Gini? = null,
    val idd: Idd? = null,
    val independent: Boolean? = null,
    val landlocked: Boolean? = null,
    val languages: Languages? = null,
    val latlng: List<Double>? = null,
    val maps: Maps? = null,
    val name: Name? = null,
    val population: Int? = null,
    val postalCode: PostalCode? = null,
    val region: String? = null,
    val startOfWeek: String? = null,
    val status: String? = null,
    val subregion: String? = null,
    val timezones: List<String>? = null,
    val tld: List<String>? = null,
    val translations: Translations? = null,
    val unMember: Boolean? = null
)
