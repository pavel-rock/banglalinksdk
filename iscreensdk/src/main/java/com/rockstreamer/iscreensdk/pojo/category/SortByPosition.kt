package com.rockstreamer.iscreensdk.pojo.category

import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem

import com.rockstreamer.iscreensdk.pojo.slider.SliderResponse


class SortByPosition : Comparator<CategoryItems> {
    override fun compare(a: CategoryItems?, b: CategoryItems?): Int {
        return a!!.position - b!!.position
    }
}

class SortByBannerPosition : Comparator<SliderResponse> {
    override fun compare(a: SliderResponse?, b: SliderResponse?): Int {
        return a!!.position - b!!.position
    }
}

class SortByEpisodesPosition : Comparator<EpisodeItem> {
    override fun compare(a: EpisodeItem?, b: EpisodeItem?): Int {
        return a!!.position - b!!.position
    }
}



