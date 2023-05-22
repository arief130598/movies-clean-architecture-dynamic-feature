package com.aplus.domain.usecases.local.genres

data class GenresUseCases (
    val deleteAll: DeleteAll,
    val deleteSingle: DeleteSingle,
    val getList: GetList,
    val getSingle: GetSingle,
    val insertList: InsertList,
    val insertSingle: InsertSingle
)