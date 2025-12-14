package com.elzren.mywatchlist.search.utils

import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.search.domain.model.SearchItem

object SearchUtils {
    val genres = listOf(
        SearchItem("Action", "28|10759", R.drawable.gun),
        SearchItem("Adventure", "12|10759", R.drawable.paragliding_24px),
        SearchItem("Animation", "16", R.drawable.pokeball),
        SearchItem("Comedy", "35", R.drawable.comedy_mask_24px),
        SearchItem("Crime", "80", R.drawable.criminal),
        SearchItem("Documentary", "99", R.drawable.documentary),
        SearchItem("Drama", "18", R.drawable.drama),
        SearchItem("Family", "10751", R.drawable.family_group_24px),
        SearchItem("Fantasy", "14|10765", R.drawable.castle),
        SearchItem("History", "36", R.drawable.swords_24px),
        SearchItem("Mystery", "9648", R.drawable.mystery_24px),
        SearchItem("Romance", "10749", R.drawable.romance),
        SearchItem("Science Fiction", "878|10765", R.drawable.ufo),
        SearchItem("Western", "37", R.drawable.western_hat),
    )
}