package es.usj.androidapps.model

import javax.persistence.*

@Entity
@Table(name = "actors")
class Actor(
    @Id
    var id: Long,
    @Column(unique = true)
    var name: String,
    @ManyToMany(mappedBy = "actors")
    val movies: MutableList<Movie> = mutableListOf()
) {
    constructor() : this(0, "")
}