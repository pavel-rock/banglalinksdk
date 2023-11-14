package com.rockstreamer.videoplayer.quality

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.google.android.exoplayer2.C
import com.rockstreamer.iscreensdk.utils.CC_DEFAULT
import com.rockstreamer.iscreensdk.utils.CC_GROUP_INDEX_MOD
import com.rockstreamer.iscreensdk.utils.ExoItem
import com.rockstreamer.iscreensdk.player.player.ExoMediaPlayerImpl
import com.rockstreamer.videoplayer.renderer.RendererType

class QualityPopupManager {
  fun getQualityItem(videoView: ExoMediaPlayerImpl): List<ExoItem> {
    val trackGroupArray = videoView.availableTracks?.get(RendererType.VIDEO)
    if (trackGroupArray == null || trackGroupArray.isEmpty) {
      return emptyList()
    }

    val items = mutableListOf<ExoItem>()
    var trackSelected = false

    for (groupIndex in 0 until trackGroupArray.length) {
      val selectedIndex = videoView.getSelectedTrackIndex(RendererType.VIDEO, groupIndex)

      Log.d("APP_STATUS", "selectedIndex: ${selectedIndex}")
      val trackGroup = trackGroupArray.get(groupIndex)

      for (index in 0 until trackGroup.length) {
        val format = trackGroup.getFormat(index)

//        // Skip over non text formats.
//        if (!format.sampleMimeType!!.startsWith("text")) {
//          continue
//        }

        //val title = format.label ?: format.label ?: "${groupIndex.toShort()}:$index"
        val title:String = "${format.height}"
        Log.d("APP_STATUS", "bitrate is: ${format.bitrate} for ${format.height}")
        val itemId = groupIndex * C.TRACK_TYPE_VIDEO + index
        items.add(ExoItem(itemId, title, index == selectedIndex))
        if (index == selectedIndex) {
          trackSelected = true
        }
      }
    }

    // Adds "Disabled" and "Auto" options
    val rendererEnabled = videoView.isRendererEnabled(RendererType.CLOSED_CAPTION)
    items.add(0, ExoItem(CC_DEFAULT, "Auto", rendererEnabled && !trackSelected))
    return items
  }

  fun showCaptionsMenu(items: List<ExoItem>, button: View, clickListener: PopupMenu.OnMenuItemClickListener) {
    val context = button.context
    val popupMenu = PopupMenu(context, button)
    val menu = popupMenu.menu

    // Add Menu Items
    items.forEach {
      val item = menu.add(0, it.id, 0, it.title)

      item.isCheckable = true
      if (it.selected) {
        item.isChecked = true
      }
    }

    menu.setGroupCheckable(0, true, true)
    popupMenu.setOnMenuItemClickListener(clickListener)
    popupMenu.show()
  }

  fun showQualityPopup(qualityPopupManager: QualityPopupManager, videoView: ExoMediaPlayerImpl, view: View) {
    val captionItems = qualityPopupManager.getQualityItem(videoView)
    if (captionItems.isEmpty()) {
      return
    }
    qualityPopupManager.showCaptionsMenu(captionItems, view) {
      onQualityTrackSelected(it, videoView)
    }
  }

  private fun onQualityTrackSelected(menuItem: MenuItem, videoView: ExoMediaPlayerImpl): Boolean {
    menuItem.isChecked = true

    when (val itemId = menuItem.itemId) {
      CC_DEFAULT -> videoView.getTrackManager().clearSelectedTracks(RendererType.VIDEO)
      else -> {
        val trackIndex = itemId % CC_GROUP_INDEX_MOD
        val groupIndex = itemId / CC_GROUP_INDEX_MOD
        videoView.setSelectedTrack(RendererType.VIDEO, groupIndex, trackIndex)
      }
    }
    return true
  }

}