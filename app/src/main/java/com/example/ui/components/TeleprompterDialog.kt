package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.SpeechResult
import com.example.ui.theme.*

@Composable
fun TeleprompterDialog(
    speechResult: SpeechResult,
    secondsLeft: Int,
    isRunning: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(WarRoomBlack)
                .testTag("teleprompter_dialog"),
            color = WarRoomBlack
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Top Teleprompter Controls Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LIVE FLOOR TELEPROMPTER",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.5.sp,
                            color = CrimsonLight
                        )
                        Text(
                            text = "${speechResult.portfolio} • ${speechResult.speechType.label}",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    // Close Button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .background(WarRoomSurfaceVariant, CircleShape)
                            .border(1.dp, WarRoomBorderGlow, CircleShape)
                            .testTag("teleprompter_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextWhite,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Countdown Timer Control Banner
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = WarRoomSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "Timer",
                                tint = if (secondsLeft <= 10) CrimsonPrimary else GoldAmber,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = if (secondsLeft <= 10) CrimsonPrimary else TextWhite
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Play / Pause
                            Button(
                                onClick = { if (isRunning) onPause() else onStart() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isRunning) CrimsonDeep else EmeraldVerified
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isRunning) "Pause" else "Start",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isRunning) "PAUSE" else "START",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Reset
                            OutlinedButton(
                                onClick = onReset,
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reset",
                                    tint = TextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Teleprompter Scrolling Speech Text
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF07080B))
                        .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        val paragraphs = speechResult.fullSpeech.split("\n\n").filter { it.isNotBlank() }
                        paragraphs.forEachIndexed { idx, para ->
                            Text(
                                text = para.trim(),
                                fontSize = 20.sp,
                                lineHeight = 32.sp,
                                fontWeight = if (idx == 0) FontWeight.Bold else FontWeight.Medium,
                                color = if (idx == 0) GoldLight else TextWhite
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        // Knockout finish
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WarRoomSurfaceVariant, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "CLOSING PUNCH: \"${speechResult.bestClosingLine}\"",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = CrimsonLight
                            )
                        }
                    }
                }
            }
        }
    }
}
