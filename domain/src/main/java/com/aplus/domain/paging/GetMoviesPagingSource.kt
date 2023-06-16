package com.aplus.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aplus.core.utils.Resource
import com.aplus.core.utils.Status
import com.aplus.domain.model.Movies
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest

class GetMoviesPagingSource @Inject constructor(
    private val repository: ApiMovieRepository
): PagingSource<Int, Movies>() {

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        val page = params.key ?: 1
        var totalPage: Int = 20

        var response: Resource<List<Movies>> = Resource.loading(null)
        try {
            repository.getPopular(
                page
            ).catch {
                response = Resource.error(it.toString(), null)
            }.collectLatest {
                if (it.body()?.results != null) {
                    it.body()?.results?.let { data ->
                        response = Resource.success(data)
                    }
                    totalPage = it.body()!!.total_pages
                } else {
                    response = Resource.error(it.message(), null)
                }
            }
            return when (response.status) {
                Status.SUCCESS -> {
                    LoadResult.Page(
                        data = response.data!!,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page < totalPage) page + 1 else null
                    )
                }
                Status.ERROR -> LoadResult.Error(Throwable(response.message))
                Status.LOADING -> LoadResult.Error(Throwable())
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}