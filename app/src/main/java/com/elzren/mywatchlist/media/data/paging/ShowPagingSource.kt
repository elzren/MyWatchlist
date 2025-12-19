package com.elzren.mywatchlist.media.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.elzren.mywatchlist.core.data.api.TmdbApiService
import com.elzren.mywatchlist.core.utils.Constants
import com.elzren.mywatchlist.home.domain.model.Show
import okio.IOException

class ShowPagingSource(
    val tmdbApi: TmdbApiService,
    val showNsfw: Boolean,
    val genres: String?,
    val keywords: String?
) :
    PagingSource<Int, Show>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Show> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = tmdbApi.getShows(
                page = nextPageNumber,
                withGenres = genres,
                withKeywords = keywords,
                withoutKeywords = if (showNsfw) null else Constants.NSFW_KEYWORDS,
//                includeAdult = showNsfw
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

    override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
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