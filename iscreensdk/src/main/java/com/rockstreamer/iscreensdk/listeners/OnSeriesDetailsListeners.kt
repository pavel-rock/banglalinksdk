package com.rockstreamer.iscreensdk.listeners

import com.rockstreamer.iscreensdk.pojo.series.EpisodeItem
import com.rockstreamer.iscreensdk.pojo.series.SeasonContent
import com.rockstreamer.iscreensdk.pojo.series.SeriesResponse
import kotlin.time.Duration

interface OnSeriesDetailsListeners {

    fun onPremiumEpisodeDecide(episodeItem: EpisodeItem)
    fun onDataSetIntoSessionAdapter(seasonContent: SeasonContent)
    fun onEpisodeInformationShow(episodeItem: EpisodeItem)
    fun onSeekForward(duration: Long)
    fun onSeekBackward(duration: Long)

    fun onEpisodeContentPlay(episodeItem: EpisodeItem)

    fun onSeriesEpisodeClick(value: EpisodeItem, position: Int)
}