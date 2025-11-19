package com.example.playground.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.example.playground.core.data.api.TmdbApiService
import com.example.playground.core.domain.model.Media
import okio.IOException

class MediaSearchPagingSource(
    val tmdbApi: TmdbApiService,
    val query: String
) : PagingSource<Int, Media>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Media> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = tmdbApi.getMediaSearchResults(query, nextPageNumber)
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

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
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