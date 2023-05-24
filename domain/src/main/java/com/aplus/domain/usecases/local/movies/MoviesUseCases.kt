package com.aplus.domain.usecases.local.movies

data class MoviesUseCases (
    val deleteAllMovies: DeleteAllMovies,
    val deleteSingleMovies: DeleteSingleMovies,
    val getListMovies: GetListMovies,
    val getSingleMovies: GetSingleMovies,
    val insertListMovies: InsertListMovies,
    val insertSingleMovies: InsertSingleMovies
)