package es.usj.androidapps.services

interface IServiceApi<T> {
    fun list(limit: Int?, offset: Long?): List<T>
    fun find(id: Long): T?
    fun delete(id: Long): T
    fun save(element: T): T
    fun edit(element: T): Int
}