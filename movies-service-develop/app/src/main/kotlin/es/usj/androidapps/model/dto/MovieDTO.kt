package es.usj.androidapps.model.dto

data class MovieDTO(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var director: String = "",
    var year: Int = 0,
    var runtime: Int = 0,
    var rating: Double = 0.0,
    var votes: Long = 0,
    var revenue: Double = 0.0,
    var actors: List<Long> = emptyList(),
    var genres: List<Long> = emptyList()
) {
    init {
        title = title.trim()
    }

    constructor(
        id: Long,
        title: String,
        description: String,
        director: String,
        year: Int,
        runtime: Int,
        rating: Int,
        votes: Long,
        revenue: Double,
        actors: List<Long> = emptyList(),
        genres: List<Long> = emptyList()
    ) : this(id, title, description, director, year, runtime, rating.toDouble(), votes, revenue, actors, genres)

    constructor(
        id: Long,
        title: String,
        description: String,
        director: String,
        year: Int,
        runtime: Int,
        rating: Int,
        votes: Long,
        revenue: Int,
        actors: List<Long> = emptyList(),
        genres: List<Long> = emptyList()
    ) : this(
        id,
        title,
        description,
        director,
        year,
        runtime,
        rating.toDouble(),
        votes,
        revenue.toDouble(),
        actors,
        genres
    )

    constructor(
        id: Long,
        title: String,
        description: String,
        director: String,
        year: Int,
        runtime: Int,
        rating: Double,
        votes: Long,
        revenue: Int,
        actors: List<Long> = emptyList(),
        genres: List<Long> = emptyList()
    ) : this(id, title, description, director, year, runtime, rating, votes, revenue.toDouble(), actors, genres)
}