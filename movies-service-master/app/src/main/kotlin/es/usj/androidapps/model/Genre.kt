package es.usj.androidapps.model

import javax.persistence.*

@Entity
@Table(name = "genres")
class Genre(
    @Id
    @GeneratedValue
    var id: Long,
    @Column(unique = true)
    var name: String,
    @ManyToMany(mappedBy = "genres")
    val movies: MutableList<Movie> = mutableListOf()
) {
    constructor() : this(0, "")
}