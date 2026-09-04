package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.WarRoomScreen
import com.example.ui.theme.*

@Composable
fun WarRoomHeader(
    currentScreen: WarRoomScreen,
    bestDelegateEnabled: Boolean,
    onToggleBestDelegate: (Boolean) -> Unit,
    onSelectScreen: (WarRoomScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarRoomBlack)
            .border(width = 1.dp, color = WarRoomBorder, shape = RoundedCornerShape(0.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Sophisticated Dark Header Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "STRATEGIC TERMINAL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GoldAmber,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "AIPPM WAR ROOM",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp,
                    color = TextWhite
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Live Research Indicator Pill (Matching Design HTML)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = WarRoomSurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow),
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(CrimsonPrimary.copy(alpha = pulseAlpha))
                        )
                        Text(
                            text = "LIVE RESEARCH",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CrimsonLight,
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                // Best Delegate Mode Toggle Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (bestDelegateEnabled) WarRoomSurfaceVariant else Color.Transparent,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (bestDelegateEnabled) GoldAmber else WarRoomBorderGlow
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onToggleBestDelegate(!bestDelegateEnabled) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("best_delegate_toggle")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Best Delegate",
                            tint = if (bestDelegateEnabled) GoldAmber else TextSubtle,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "BEST DELEGATE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (bestDelegateEnabled) GoldAmber else TextSubtle,
                            letterSpacing = 0.5.sp
                        )
                        Switch(
                            checked = bestDelegateEnabled,
                            onCheckedChange = onToggleBestDelegate,
                            modifier = Modifier
                                .height(18.dp)
                                .testTag("best_delegate_switch"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldAmber,
                                checkedTrackColor = GoldDark,
                                uncheckedThumbColor = TextSubtle,
                                uncheckedTrackColor = WarRoomBorder
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Navigation Tabs Bar (Sophisticated Dark aesthetic)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WarRoomScreen.entries.forEach { screen ->
                val isSelected = currentScreen == screen
                val tabIcon = when (screen) {
                    WarRoomScreen.SPEECH_LAB -> Icons.Default.Mic
                    WarRoomScreen.ATTACK_DEFENCE -> Icons.Default.Gavel
                    WarRoomScreen.RAPID_REBUTTAL -> Icons.Default.FlashOn
                    WarRoomScreen.CONTRADICTIONS_TIMELINE -> Icons.Default.History
                    WarRoomScreen.DOSSIER -> Icons.Default.MenuBook
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) CrimsonDark else WarRoomSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) CrimsonBright else WarRoomBorderGlow
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectScreen(screen) }
                        .testTag("nav_${screen.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = tabIcon,
                            contentDescription = screen.title,
                            tint = if (isSelected) Color.White else TextMuted,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = screen.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextHighlight,
                            letterSpacing = 0.3.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) CrimsonBright else WarRoomSurfaceVariant)
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = screen.badge,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else GoldAmber
                            )
                        }
                    }
                }
            }
        }
    }
}

