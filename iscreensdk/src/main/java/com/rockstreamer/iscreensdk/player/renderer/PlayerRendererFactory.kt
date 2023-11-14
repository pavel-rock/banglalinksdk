/*
 * Copyright (C) 2021 ExoMedia Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rockstreamer.videoplayer.renderer

import android.content.Context
import android.os.Handler
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.metadata.MetadataOutput
import com.google.android.exoplayer2.text.TextOutput
import com.google.android.exoplayer2.video.VideoRendererEventListener
import com.rockstreamer.videoplayer.renderer.provider.AudioRenderProvider
import com.rockstreamer.videoplayer.renderer.provider.CaptionRenderProvider
import com.rockstreamer.videoplayer.renderer.provider.MetadataRenderProvider
import com.rockstreamer.videoplayer.renderer.provider.VideoRenderProvider

class PlayerRendererFactory(
  private val context: Context
): RenderersFactory {
  override fun createRenderers(
    eventHandler: Handler,
    videoRendererEventListener: VideoRendererEventListener,
    audioRendererEventListener: AudioRendererEventListener,
    textRendererOutput: TextOutput,
    metadataRendererOutput: MetadataOutput
  ): Array<Renderer> {
    return mutableListOf<Renderer>().apply {
      addAll(AudioRenderProvider().buildRenderers(context, eventHandler, audioRendererEventListener))
      addAll(VideoRenderProvider().buildRenderers(context, eventHandler, videoRendererEventListener))
      addAll(CaptionRenderProvider().buildRenderers(eventHandler, textRendererOutput))
      addAll(MetadataRenderProvider().buildRenderers(eventHandler, metadataRendererOutput))
    }.toTypedArray()
  }
}