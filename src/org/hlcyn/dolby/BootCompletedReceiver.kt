/*
 * Copyright (C) 2023-2024 Paranoid Android
 * Copyright (C) 2024-2026 Halcyon Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hlcyn.dolby

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.hlcyn.dolby.DolbyConstants.Companion.dlog

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        dlog(TAG, "Received action: $action")
        val controller = DolbyController.getInstance(context)
        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> controller.onBootCompleted()
            DolbyConstants.ACTION_SET_DOLBY_STATE -> {
                if (intent.hasExtra(DolbyConstants.EXTRA_STATE)) {
                    val state = intent.getBooleanExtra(DolbyConstants.EXTRA_STATE, false)
                    dlog(TAG, "Setting Dolby state via broadcast: $state")
                    controller.dsOn = state
                }
            }
            DolbyConstants.ACTION_TOGGLE_DOLBY -> {
                val newState = controller.toggleDsOn()
                dlog(TAG, "Toggled Dolby state via broadcast: $newState")
            }
        }
    }

    companion object {
        private const val TAG = "BootCompletedReceiver"
    }
}
