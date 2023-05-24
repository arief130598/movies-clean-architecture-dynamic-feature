package com.aplus.domain.usecases.local.genres

data class GenresUseCases (
    val deleteAllGenres: DeleteAllGenres,
    val deleteSingleGenres: DeleteSingleGenres,
    val getListGenres: GetListGenres,
    val getSingleGenres: GetSingleGenres,
    val insertListGenres: InsertListGenres,
    val insertSingleGenres: InsertSingleGenres
)