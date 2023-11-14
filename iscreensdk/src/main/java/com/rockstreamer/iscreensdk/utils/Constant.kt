package com.rockstreamer.iscreensdk.utils

import android.content.SharedPreferences


const val BASE_URL = "https://api.rockstreamer.com/"
const val API_TOKEN = "api_token"
lateinit var loginState: SharedPreferences
const val ISCREEN_SHAREDPREFERANCE = "iscreen_shared_preference"


const val BASE_BANNER_IMAGE = "https://img.rockstreamer.com/600x350/"
const val BASE_CATEGORY_VIDEO_IMAGE = "https://img.rockstreamer.com/400xauto/"
const val BASE_ALBUM_IMAGE = "https://img.rockstreamer.com/400xauto/"
const val BASE_MOVIE_POSTER = "https://img.rockstreamer.com/250xauto/"

const val AVATAR = " https://i.imgur.com/SFuwc2c.jpg"

const val VIDEO_CONTENT = 0
const val SERIES_CONTENT = 3
const val VIDEO_CONTENT_VERTICAL = 8
const val VIDEO_CONTENT_HORIZONTAL = 9
const val IMAGE_VERTICAL = "vertical"
const val IMAGE_HORIZONTAL = "horizontal"
const val PADDING = 20
const val ITEM_SPACE = 25
const val IMAGE_URL = "https://graphic.rockstreamer.com/"

const val VIDEO_ID_PASS = "video_id_pass"
const val SERIES_ID_PASS = "Series_id_pass"


const val EXTRA_SEE_MORE_ID = "see_more_id"
const val EXTRA_SEEMORE_ORIENTATION = "extra_orientation"
const val EXTRA_SEEMORE_TITLE = "extra_title"