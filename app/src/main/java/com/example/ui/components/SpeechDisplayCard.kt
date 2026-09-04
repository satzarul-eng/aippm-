package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ClaimCategory
import com.example.data.SpeechResult
import com.example.ui.theme.*

@Composable
fun SpeechDisplayCard(
    speechResult: SpeechResult,
    activeTab: Int,
    onTabChange: (Int) -> Unit,
    onApplyModifier: (String) -> Unit,
    onRegenerate: () -> Unit,
    onOpenTeleprompter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var copyConfirmed by remember { mutableStateOf(false) }

    LaunchedEffect(copyConfirmed) {
        if (copyConfirmed) {
            kotlinx.coroutines.delay(2000)
            copyConfirmed = false
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("speech_display_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = WarRoomSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Title + Delivery Metrics + Quick Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "PARLIAMENTARY SPEECH",
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CrimsonDeep)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = speechResult.speechType.name,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                    Text(
                        text = "${speechResult.wordCount} words • ~${speechResult.estimatedSeconds}s spoken pace (135 wpm)",
                        fontSize = 11.sp,
                        color = GoldAmber
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Teleprompter Button
                    IconButton(
                        onClick = onOpenTeleprompter,
                        modifier = Modifier
                            .size(36.dp)
                            .background(WarRoomSurfaceVariant, RoundedCornerShape(8.dp))
                            .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(8.dp))
                            .testTag("open_teleprompter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Live Teleprompter",
                            tint = GoldAmber,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Copy Speech Button
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("AIPPM Speech", speechResult.fullSpeech)
                            clipboard.setPrimaryClip(clip)
                            copyConfirmed = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (copyConfirmed) EmeraldVerified else CrimsonDeep
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(36.dp)
                            .border(
                                1.dp,
                                if (copyConfirmed) EmeraldVerified else CrimsonBright,
                                RoundedCornerShape(8.dp)
                            )
                            .testTag("copy_speech_button")
                    ) {
                        Icon(
                            imageVector = if (copyConfirmed) Icons.Default.Check else Icons.Default.ContentCopy,
                            contentDescription = "Copy Speech",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (copyConfirmed) "COPIED" else "COPY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Sub-Tabs Navigation
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WarRoomSurfaceVariant, RoundedCornerShape(8.dp))
                    .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(8.dp))
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val tabs = listOf("Delivered Speech", "Key Attacks & POIs", "Fact-Check Dossier", "Legal & Sources")
                tabs.forEachIndexed { index, title ->
                    val isSelected = activeTab == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) CrimsonDeep else Color.Transparent)
                            .clickable { onTabChange(index) }
                            .padding(vertical = 7.dp)
                            .testTag("output_tab_$index"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextMuted,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Content according to selected Sub-Tab
            when (activeTab) {
                0 -> {
                    // TAB 0: DELIVERED SPEECH
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(WarRoomBlack, RoundedCornerShape(10.dp))
                            .border(1.dp, WarRoomBorderGlow, RoundedCornerShape(10.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            // Speech Paragraphs formatted with high readability
                            val paragraphs = speechResult.fullSpeech.split("\n\n").filter { it.isNotBlank() }
                            paragraphs.forEachIndexed { idx, p ->
                                Text(
                                    text = p.trim(),
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp,
                                    fontWeight = if (idx == 0) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (idx == 0) TextHighlight else TextWhite
                                )
                                if (idx < paragraphs.size - 1) {
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(color = WarRoomBorder, thickness = 1.dp)
                            Spacer(modifier = Modifier.height(10.dp))

                            // Knockout Closing Highlight
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Campaign,
                                    contentDescription = "Closing",
                                    tint = GoldAmber,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "KNOCKOUT CLOSING LINE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldAmber,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${speechResult.bestClosingLine}\"",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                color = TextWhite
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Quick Tuning Modifier Chips
                    Text(
                        text = "LIVE SPEECH TUNING CONTROLS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSubtle,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ModifierChip(label = "Make More Aggressive", icon = Icons.Default.Whatshot, onClick = { onApplyModifier("MAKE_MORE_AGGRESSIVE") })
                        ModifierChip(label = "Make More Human", icon = Icons.Default.RecordVoiceOver, onClick = { onApplyModifier("MAKE_MORE_HUMAN") })
                        ModifierChip(label = "Add More Facts", icon = Icons.Default.Assessment, onClick = { onApplyModifier("ADD_MORE_FACTS") })
                        ModifierChip(label = "Add Constitutional Citations", icon = Icons.Default.Gavel, onClick = { onApplyModifier("ADD_CONSTITUTIONAL") })
                        ModifierChip(label = "Make Shorter (Cut 30%)", icon = Icons.Default.Compress, onClick = { onApplyModifier("MAKE_SHORTER") })
                        ModifierChip(label = "Make Longer (Elaborate)", icon = Icons.Default.Expand, onClick = { onApplyModifier("MAKE_LONGER") })
                        ModifierChip(label = "Make More Controversial", icon = Icons.Default.Warning, onClick = { onApplyModifier("MAKE_MORE_CONTROVERSIAL") })
                        ModifierChip(label = "Make More Diplomatic", icon = Icons.Default.Handshake, onClick = { onApplyModifier("MAKE_MORE_DIPLOMATIC") })
                    }
                }

                1 -> {
                    // TAB 1: KEY ATTACKS & POIS
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "STRATEGIC ATTACK VECTORS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonLight,
                            letterSpacing = 0.5.sp
                        )

                        speechResult.keyAttacks.forEach { attack ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricBolt,
                                        contentDescription = "Attack Vector",
                                        tint = CrimsonPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = attack,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "LETHAL POINTS OF INFORMATION (POIs)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAmber,
                            letterSpacing = 0.5.sp
                        )

                        speechResult.possiblePois.forEach { poi ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldDark)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(GoldDark)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "TARGET: ${poi.target}",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = poi.objective,
                                            fontSize = 10.sp,
                                            color = TextMuted
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "\"${poi.question}\"",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextWhite
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Trap: ${poi.rhetoricalTrap}",
                                        fontSize = 10.sp,
                                        color = GoldLight
                                    )
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: FACT CHECK DOSSIER
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "VERIFIED FACT AUDIT",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldVerified,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "ZERO UNVERIFIED ALLEGATIONS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldVerified
                            )
                        }

                        speechResult.factChecks.forEach { fact ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = WarRoomSurfaceVariant),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(fact.tier.badgeColor))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = fact.tier.label,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(EmeraldDark)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = "VERIFIED (${fact.confidencePercent}%)",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = EmeraldVerified
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = fact.claim,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextWhite
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Source: ${fact.source} (${fact.date})",
                                        fontSize = 10.sp,
                                        color = TextHighlight
                                    )
                                    Text(
                                        text = "Why trustworthy: ${fact.trustworthyReason}",
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // TAB 3: LEGAL & SOURCES
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "CONSTITUTIONAL & STATUTORY CITATIONS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueAuthority,
                            letterSpacing = 0.5.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "ARTICLES CITED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                                    speechResult.keyArticles.forEach { art ->
                                        Text(text = "• $art", fontSize = 11.sp, color = TextWhite)
                                    }
                                }
                            }

                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "STATUTES & LAWS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextSubtle)
                                    speechResult.keyLaws.forEach { law ->
                                        Text(text = "• $law", fontSize = 11.sp, color = TextWhite)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "PRIMARY RESEARCH SOURCES CONSULTED",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            letterSpacing = 0.5.sp
                        )

                        speechResult.sources.forEach { src ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = WarRoomSurfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = src.title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                                        Text(text = src.consultedTopic, fontSize = 10.sp, color = TextMuted)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(src.tier.badgeColor))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = src.tier.name,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModifierChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = WarRoomSurfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, WarRoomBorderGlow),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = GoldAmber, modifier = Modifier.size(13.dp))
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextWhite)
        }
    }
}
