package es.usj.androidapps.services

import es.usj.androidapps.model.dto.MovieDTO
import org.springframework.stereotype.Service

@Service
interface MovieServiceApi : IServiceApi<MovieDTO>