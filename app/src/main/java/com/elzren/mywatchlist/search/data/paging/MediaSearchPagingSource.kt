package com.elzren.mywatchlist.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.domain.model.MediaModel
import okio.IOException

class MediaSearchPagingSource(
    val tmdbApi: TmdbApiService,
    val query: String,
    val showNsfw: Boolean
) : PagingSource<Int, MediaModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MediaModel> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = tmdbApi.getMediaSearchResults(
                query = query,
                page = nextPageNumber,
                includeAdult = showNsfw
            )
            return LoadResult.Page(
                data = response.results,
                prevKey = null, // Only paging forward.
                nextKey = if (response.page != response.totalPages) nextPageNumber + 1 else null,
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MediaModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}