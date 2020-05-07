package ua.com.cuteteam.cutetaxi.api.autocomplete

data class Autocomplete(
    val status: String,
    val predictions: List<Place>
){
    val places: List<String> = predictions.map { it.description }
}

data class Place(
    val description: String
)