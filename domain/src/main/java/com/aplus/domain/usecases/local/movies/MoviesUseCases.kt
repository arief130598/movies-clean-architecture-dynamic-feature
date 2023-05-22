package com.aplus.domain.usecases.local.movies

data class MoviesUseCases (
    val deleteAll: DeleteAll,
    val deleteSingle: DeleteSingle,
    val getList: GetList,
    val getSingle: GetSingle,
    val insertList: InsertList,
    val insertSingle: InsertSingle
)