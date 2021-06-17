/*
 * Copyright (c) 2020-2021 Aurora, Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of the copyright holder nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.pushingpixels.aurora.demo

import androidx.compose.desktop.SwingPanel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import org.pushingpixels.aurora.auroraBackground
import org.pushingpixels.aurora.component.model.Command
import org.pushingpixels.aurora.component.projection.CommandButtonProjection
import org.pushingpixels.aurora.skin.marinerSkin
import org.pushingpixels.aurora.window.AuroraWindow
import org.pushingpixels.substance.api.SubstanceCortex
import org.pushingpixels.substance.api.skin.MarinerSkin
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JPanel

fun main() {
    GlobalScope.launch(Dispatchers.Swing) {
        SubstanceCortex.GlobalScope.setSkin(MarinerSkin())
    }
    AuroraWindow(
        skin = marinerSkin(),
        title = "Aurora Demo",
        size = IntSize(200, 150),
        undecorated = true
    ) {
        var text by remember { mutableStateOf("Hello, World!") }

        Column(modifier = Modifier.fillMaxSize()) {
            // This is native Compose content rendered by Aurora
            Row(
                modifier = Modifier.fillMaxWidth().auroraBackground().padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CommandButtonProjection(
                    contentModel = Command(
                        text = text,
                        action = { text = "Hello, Desktop!" }
                    )
                ).project()
            }

            // This is Swing content rendered by Substance look-and-feel
            SwingPanel(
                modifier = Modifier.fillMaxWidth().height(90.dp),
                factory = {
                    JPanel().apply {
                        layout = FlowLayout(FlowLayout.CENTER)
                        add(swingButton("Hello, Swing!") { println("I come from Swing") })
                    }
                }
            )
        }
    }
}

fun swingButton(text: String, action: () -> Unit): JButton {
    val button = JButton(text)
    button.addActionListener { action.invoke() }

    return button
}
