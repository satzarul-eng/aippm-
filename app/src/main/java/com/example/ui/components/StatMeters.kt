package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AggressionLevel
import com.example.data.ControversyLevel
import com.example.data.ResearchDepth
import com.example.ui.theme.*

@Composable
fun CentralCommandDashboard(
    portfolio: String,
    party: String,
    agenda: String,
    target: String,
    speechTimeSeconds: Int,
    aggression: AggressionLevel,
    researchDepth: ResearchDepth,
    isGenerating: Boolean,
    progressText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("central_command_dashboard"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Dashboard Status Bar (Sophisticated Dark Terminal aesthetic)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(if (isGenerating) CrimsonPrimary else EmeraldVerified)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isGenerating) "RESEARCH ENGINE ACTIVE" else "STRATEGIC TERMINAL READY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isGenerating) CrimsonLight else EmeraldVerified,
                        letterSpacing = 1.sp
                    )
                }

                // Status indicator dashes (from Design HTML)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(GoldAmber)
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(GoldAmber)
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 16.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(TextSlate700)
                    )
                }
            }

            AnimatedVisibility(visible = isGenerating) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = CrimsonBright,
                        trackColor = WarRoomBorderGlow
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = progressText,
                        fontSize = 11.sp,
                        color = GoldAmber,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = WarRoomBorderSubtle, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // 4-Grid Key Metrics
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CommandMetricPill(
                    label = "PORTFOLIO",
                    value = portfolio,
                    subtext = party,
                    icon = Icons.Default.AccountCircle,
                    accentColor = GoldAmber,
                    modifier = Modifier.weight(1f)
                )
                CommandMetricPill(
                    label = "TARGET",
                    value = if (target.isBlank()) "Opposition Frontbenchers" else target,
                    subtext = "Cross-Exam Focus",
                    icon = Icons.Default.Adjust,
                    accentColor = CrimsonLight,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CommandMetricPill(
                    label = "LIMIT",
                    value = "${speechTimeSeconds} SEC",
                    subtext = "~${(speechTimeSeconds * 2.1).toInt()} words limit",
                    icon = Icons.Default.Timer,
                    accentColor = TextWhite,
                    modifier = Modifier.weight(1f)
                )
                CommandMetricPill(
                    label = "DEPTH",
                    value = researchDepth.label.uppercase(),
                    subtext = "${aggression.level}/5 Aggression",
                    icon = Icons.Default.Gavel,
                    accentColor = BlueAuthority,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CommandMetricPill(
    label: String,
    value: String,
    subtext: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = WarRoomSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSubtle,
                    letterSpacing = 1.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = accentColor,
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtext,
                fontSize = 9.sp,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AggressionSliderCard(
    aggression: AggressionLevel,
    onAggressionChange: (AggressionLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AGGRESSION",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSubtle,
                    letterSpacing = 1.sp
                )
                Text(
                    text = aggression.label.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CrimsonLight,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Continuous visual gradient meter (matching Design HTML)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(TextSlate800)
            ) {
                val progressFraction = aggression.level / 5f
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    CrimsonDeep,
                                    CrimsonBright,
                                    CrimsonPrimary
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = aggression.description,
                fontSize = 11.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 5-segment selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AggressionLevel.entries.forEach { level ->
                    val isSelected = aggression == level
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) CrimsonDeep else WarRoomSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) CrimsonBright else WarRoomBorderGlow,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { onAggressionChange(level) }
                            .testTag("aggression_level_${level.level}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${level.level}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextSubtle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ControversySliderCard(
    controversy: ControversyLevel,
    onControversyChange: (ControversyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CONTROVERSY",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSubtle,
                    letterSpacing = 1.sp
                )
                Text(
                    text = controversy.label.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = GoldAmber,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Continuous visual gradient meter (matching Design HTML)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(TextSlate800)
            ) {
                val progressFraction = controversy.level / 5f
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF78350F),
                                    GoldDark,
                                    GoldAmber
                                )
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${controversy.description} (Note: Increased controversy never invents unverified facts)",
                fontSize = 11.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ControversyLevel.entries.forEach { level ->
                    val isSelected = controversy == level
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) GoldDark else WarRoomSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) GoldAmber else WarRoomBorderGlow,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { onControversyChange(level) }
                            .testTag("controversy_level_${level.level}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${level.level}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else TextSubtle
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResearchDepthSelector(
    selectedDepth: ResearchDepth,
    onSelectDepth: (ResearchDepth) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "RESEARCH DEPTH PROTOCOL",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = TextSubtle,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ResearchDepth.entries.forEach { depth ->
                val isSelected = selectedDepth == depth
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSelectDepth(depth) }
                        .testTag("depth_${depth.name.lowercase()}"),
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) CrimsonDeep else WarRoomSurface,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) CrimsonBright else WarRoomBorderGlow
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = depth.label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isSelected) Color.White else TextMuted,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}

