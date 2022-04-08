package es.usj.androidapps

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan(basePackages = ["es.usj.androidapps"])
@SpringBootApplication
class MoviesServiceMsApplication

fun main(args: Array<String>) {
    runApplication<MoviesServiceMsApplication>(*args)
}