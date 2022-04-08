package es.usj.androidapps.model

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "movies")
class Movie(
    @Id
    @GeneratedValue
    var id: Long,
    @Column(length = 250)
    val title: String,
    @Column(columnDefinition = "TEXT")
    val description: String,
    @Column
    val director: String,
    @Column(name = "release_year")
    val year: Int,
    @Column
    val runtime: Int,
    @Column
    val rating: Double,
    @Column
    val votes: Long,
    @Column
    val revenue: Double,
    @ManyToMany(cascade = [CascadeType.ALL])
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "actors_to_movies",
        joinColumns = [JoinColumn(name = "id_actor")],
        inverseJoinColumns = [JoinColumn(name = "id_movie")]
    )
    val actors: MutableList<Actor> = mutableListOf(),
    @ManyToMany(cascade = [CascadeType.ALL])
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
        name = "genres_to_movies",
        joinColumns = [JoinColumn(name = "id_genre")],
        inverseJoinColumns = [JoinColumn(name = "id_movie")]
    )
    val genres: MutableList<Genre> = mutableListOf()
) {
    constructor() : this(0, "", "", "", 0, 0, 0.0, 0, 0.0)

    fun addAllGenres(genres: List<Genre>) {
        genres.forEach { addGenre(it) }
    }

    fun addGenre(genre: Genre) {
        genre.movies.add(this)
        this.genres.add(genre)
    }

    fun addAllActors(actors: List<Actor>) {
        actors.forEach { addActor(it) }
    }

    fun addActor(actor: Actor) {
        actor.movies.add(this)
        this.actors.add(actor)
    }
}