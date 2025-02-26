package com.hussein.androidprojectstandard.data.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hussein.androidprojectstandard.domain.base.Result
import kotlinx.serialization.Serializable

@Suppress("ConstPropertyName")
class BasePagingDataSource<T : Any>(
    val pageSize: Int = DefaultPageSize,
    private val pageRequest: suspend (pageIndex: Int, pageSize: Int) -> Result<PaginatedResponse<T>>,
) : PagingSource<Int, T>() {

    companion object {
        const val DefaultPageSize = 10
        private const val InitialPageNumber = 0
    }

    override val jumpingSupported: Boolean
        get() = true

    val pager = Pager(
        config = PagingConfig(pageSize, enablePlaceholders = true, prefetchDistance = pageSize),
        pagingSourceFactory = { BasePagingDataSource(pageSize, pageRequest) }
    )

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val nextPageIndex = params.key ?: InitialPageNumber
        val pageResult = pageRequest(nextPageIndex, pageSize)
        if (pageResult is Result.Success.Data) {
            return LoadResult.Page(
                pageResult.data.result.items,
                prevKey = if (nextPageIndex == InitialPageNumber) null else nextPageIndex - 1,
                nextKey = if (pageResult.data.result.items.size < pageSize) null else nextPageIndex + 1,
                itemsAfter = params.key?.let { pageResult.data.result.totalCount - it * pageSize }
                    ?: 0,
                itemsBefore = params.key?.let { ((it - 1) * pageSize).coerceAtLeast(0) } ?: 0
            )
        }

        return LoadResult.Error(IllegalStateException("An error has occurred, please try again"))

    }
}

@Serializable
data class PaginatedResponse<T>(
    val result: PaginatedResult<T>,
)

@Serializable
data class PaginatedResult<T>(
    val totalCount: Int,
    val items: List<T>
)